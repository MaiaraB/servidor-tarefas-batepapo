package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

	private Socket socket;

	public DistribuirTarefas(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			System.out.println("Distribuindo tarefas para " + socket);
			
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			while (entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println(Thread.currentThread().getName() + ": " + comando);
				
				ServidorTarefas.distribuiMensagens(Thread.currentThread().getName() + ": " + comando);
				
				//System.out.println(comando);
			}

			entradaCliente.close();
			saidaCliente.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
