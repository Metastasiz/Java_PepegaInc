public class Frame {
    final static boolean debug = false;
    static Thread t;
    Frame(){
        t = new Thread(new Run01());
        t.start();
    }
    public static void main(String[] arg){
        new Frame();
    }
}
