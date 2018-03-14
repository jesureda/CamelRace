package sureda.studium;

import java.util.Scanner;

//Implementamos Runnable para poder crear hilos sobre sus instancias
public class camelRace implements Runnable {
	private static posicionesCarrera[] pc;
	static Scanner scan = new Scanner(System.in);
	private static Thread[] hilo;
	static int participantes = 0, posicionesTotales = 0;
	boolean ganador = true;
	posicionesCarrera pcAux;
	int primero;

	public void run() {
		while (ganador) {
			for (int i = 1; i <= participantes; i++) {
				if (Thread.currentThread().getName().equals("Camello " + i)) {
					gestionaPosicionesCarrera(pc[i].tirada(), pc[i]);
				}
			}
		}
	}

	// Gestiona el avance de posiciones de cada participante e imprime el avance
	public synchronized void gestionaPosicionesCarrera(int posicionesTirada, posicionesCarrera pos) {
		if (pos.getPosicionesLibres() > 0) {
			System.out.println(Thread.currentThread().getName() + " avanza " + posicionesTirada + " posiciones");
			try {
				Thread.sleep(1000); // dormimos el hilo durante un segundo
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			pos.posicionesRestantes(posicionesTirada);
			System.out.println(Thread.currentThread().getName() + " Se encuentra en la posición: "
					+ (posicionesTotales - pos.getPosicionesLibres()));
			primero = pos.getMax(pc);
			if (pc[primero].getPosicionesLibres() == posicionesTotales) {
				System.out.println("Los participantes se lo están tomando con calma");
				System.out.println();
			} else {
				if (Thread.currentThread().getName().equals(hilo[primero].getName())) {
					System.out.println(Thread.currentThread().getName() + " va en cabeza!");
					System.out.println();
				} else {
					System.out.println("Está a " + (pos.getPosicionesLibres() - pc[primero].getPosicionesLibres())
							+ " posiciones de " + hilo[primero].getName() + ", que lidera la carrera");
					System.out.println();
				}
			}
			if (pos.getPosicionesLibres() <= 0) {
				System.out.println("¡Has llegado a la meta! " + Thread.currentThread().getName() + " es el ganador ");
				System.out.println();
				getPodio(pc, hilo);
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Número de participantes: ");
		participantes = scan.nextInt();
		System.out.println("Longitud de la carrera: ");
		posicionesTotales = scan.nextInt();
		pc = new posicionesCarrera[participantes + 1];
		hilo = new Thread[participantes + 1];
		camelRace objAg = new camelRace();
		for (int i = 1; i <= participantes; i++) {
			hilo[i] = new Thread(objAg);
			hilo[i].setName("Camello " + i);
			hilo[i].start();
			pc[i] = new posicionesCarrera();
		}
	}

	public static int getLongitudCarrera() {
		// TODO Auto-generated method stub
		return posicionesTotales;
	}

	// Ordena los corredores al final de la carrera para ver el resultado
	public void getPodio(posicionesCarrera[] podio, Thread[] camel) {
		int n = podio.length;
		posicionesCarrera temp = new posicionesCarrera();
		Thread temp2 = new Thread();

		for (int i = 1; i <= n; i++) {
			for (int j = 2; j <= (n - i); j++) {
				if (podio[j - 1].getPosicionesLibres() > podio[j].getPosicionesLibres()) {
					temp = podio[j - 1];
					podio[j - 1] = podio[j];
					podio[j] = temp;

					temp2 = camel[j - 1];
					camel[j - 1] = camel[j];
					camel[j] = temp2;
				}
			}
		}
		System.out.println("Este es el resultado de la carrera:");
		System.out.println("=======================================");
		for (int i = 1; i < n; i++) {
			if (podio[i].getPosicionesLibres() <= 0) {
				System.out.println("En " + i + "º posición " + camel[i].getName());
			} else {
				System.out.println("En " + i + "º posición " + camel[i].getName() + " a "
						+ podio[i].getPosicionesLibres() + " posiciones de la meta");
			}
		}
	}
}
