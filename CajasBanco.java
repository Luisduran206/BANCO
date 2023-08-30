import java.util.LinkedList;
import java.util.Queue;

public class CajasBanco extends Thread {

    Queue<Integer> list = new LinkedList<Integer>();
    int randomTime;

    public CajasBanco(Queue<Integer> list, int randomTime) {
        this.list = list;
        this.randomTime = randomTime;
    }

    public void run() {
        String cajaName = Thread.currentThread().getName();
        while (!list.isEmpty()) {
            int cliente = list.poll();
            atenderCliente(cliente, cajaName);
        }
    }

    private synchronized void atenderCliente(int cliente, String cajaName) {
        try {
            Thread.sleep(randomTime * 500);
            System.out.println("Se atendió al cliente " + cliente + " en la " + cajaName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Queue<Integer> clientes = new LinkedList<>();
        for (int i = 1; i <= 10; i++) {
            clientes.add(i);
        }

        int numCajas = 4;
        int randomTime = 1;

        Thread[] cajas = new Thread[numCajas];
        for (int i = 0; i < numCajas; i++) {
            cajas[i] = new CajasBanco(new LinkedList<>(clientes), randomTime);
            cajas[i].setName("Caja " + (i + 1));
            cajas[i].start();
        }

        for (Thread caja : cajas) {
            try {
                caja.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Todas las cajas han terminado de atender.");
    }
}