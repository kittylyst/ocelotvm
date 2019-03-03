package ocelot.rt;

/**
 * The per-instance metadata, effectively the mark word
 * 
 * @author ben
 */
public class OtMeta {
    private boolean live = true;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
    
    
}
