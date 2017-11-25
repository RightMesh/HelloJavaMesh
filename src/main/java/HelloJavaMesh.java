/*
 * Hello Java RightMesh
 */

import io.left.rightmesh.id.MeshID;
import io.left.rightmesh.mesh.JavaMeshManager;
import io.left.rightmesh.mesh.MeshManager;

import java.io.Console;
import java.lang.Thread; 
import java.util.HashSet;

import static io.left.rightmesh.mesh.MeshManager.DATA_RECEIVED;
import static io.left.rightmesh.mesh.MeshManager.PEER_CHANGED;
import static io.left.rightmesh.mesh.MeshManager.REMOVED;

public class HelloJavaMesh  {
    
    private static final int HELLO_PORT = 9876;
    JavaMeshManager mm;
    HashSet<MeshID> users;
    
    public static void main(String[] args) {
        System.out.println("Starting up the mesh.");
        
        try {
            HelloJavaMesh app = new HelloJavaMesh();
        } catch(Exception ex) {
            System.out.println("Something went wrong with mesh: " 
                + ex.toString());
        }
    }
    
    public HelloJavaMesh() throws Exception {
        users = new HashSet<>();
        
        //start up a non-superpeer right mesh
        mm = new JavaMeshManager(false);
        
        //bind the mesh port
        mm.bind(HELLO_PORT);
        
        //set the handling functions (requires java8 or a lambda backport
        //like retrolamba (otherwise see the android hello mesh to see
        //how to do it without any of these)
        mm.on(PEER_CHANGED, this::handlePeerChanged);
        mm.on(DATA_RECEIVED, this::handleDataReceived);
        
        //try to get user input to send messages to all other peers
        System.out.println("Enter messages to send to all other peers");
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            mm.stop();
            return;
        }
            
        String msg;
        do {
            msg = c.readLine();
            if(msg == null || msg.equals("")) { 
                break;
            }
            byte[] testData = msg.getBytes();
            for(MeshID peer : users) {
                mm.sendDataReliable(peer, HELLO_PORT, testData);
            }
        } while(msg != null && !msg.equals(""));
        
        System.out.println("Shutting down RightMesh");
        mm.stop();
    }
    
    /**
     * Handles peer update events from the mesh - maintains a list of peers and updates the display.
     *
     * @param e event object from mesh
     */
    private void handlePeerChanged(MeshManager.RightMeshEvent e) {
        // Update peer list.
        MeshManager.PeerChangedEvent event = (MeshManager.PeerChangedEvent) e;
        if (event.state != REMOVED && !users.contains(event.peerUuid)) {
            users.add(event.peerUuid);
            System.out.println("PEER JOINED: " + event.peerUuid);
        } else if (event.state == REMOVED){
            users.remove(event.peerUuid);
            System.out.println("PEER LEFT: " + event.peerUuid);
        }
    }
    
    /**
     * Handles incoming data events from the mesh - toasts the contents of the data.
     * TODO: handle incorrect types here - what if the event isn't actually
     * a data generation event (because the dev registered the wrong function)
     * - what if the data isn't a string, or can't be decoded correctly?
     *
     * @param e event object from mesh
     */
    private void handleDataReceived(MeshManager.RightMeshEvent e) {
        MeshManager.DataReceivedEvent event = (MeshManager.DataReceivedEvent) e;
        String message = new String(event.data);
        System.out.println("Received from " + event.peerUuid + ":" + message);
    }
}
