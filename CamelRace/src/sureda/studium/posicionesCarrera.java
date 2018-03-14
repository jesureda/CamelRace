package sureda.studium;

import java.util.Random;

class posicionesCarrera {
	// Tomamos la longitud de carrera indicada por teclado
	private int posicionesTotales = camelRace.getLongitudCarrera();

	public void posicionesRestantes(int posicionesTirada) {
		posicionesTotales = posicionesTotales - posicionesTirada;
	}
	
	public int getPosicionesLibres() {
		return posicionesTotales;
	}

	// Genera la tirada de cada participante y devuelve los puntos obtenidos
	public int tirada() {
		Random r = new Random();
		int puntos = 0;
		int min = 0;
		int max = 99;
		int result = r.nextInt(max - min) + min;
		if (result >= 0 && result < 30) {
			puntos = 0;
		} else if (result >= 30 && result < 70) {
			puntos = 1;
		} else if (result >= 70 && result < 90) {
			puntos = 2;
		} else {
			puntos = 3;
		}
		return puntos;
	}

	// Devuelve el indice del corredor más adelantado
	public int getMax(posicionesCarrera[] lista) {
		int maxIndice = 1;
		for (int i = 1; i < lista.length; i++) {
			int nuevo = lista[i].getPosicionesLibres();
			if ((nuevo <= lista[maxIndice].getPosicionesLibres())) {
				maxIndice = i;
			}
		}
		return maxIndice;
	}
}
