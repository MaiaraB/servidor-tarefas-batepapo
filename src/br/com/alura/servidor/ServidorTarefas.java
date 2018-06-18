package br.com.alura.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
	private static List<PrintStream> saidasClientes = new ArrayList<>();

	public static void distribuiMensagens(String msg) {
		for (PrintStream cliente : saidasClientes) {
			cliente.println(msg);
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("--- Iniciando servidor ---");
		ServerSocket servidor = new ServerSocket(12345);
		
//		ExecutorService threadPool = Executors.newFixedThreadPool(2);
//		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		while (true) {
			Socket socket = servidor.accept();
			saidasClientes.add(new PrintStream(socket.getOutputStream()));
			System.out.println("Aceitando novo cliente na porta " + socket.getPort());
			
			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
//			Thread threadCliente = new Thread(distribuirTarefas);
//			threadCliente.start();
//			scheduledThreadPool.scheduleAtFixedRate(distribuirTarefas, 0, 60, TimeUnit.MINUTES);
			threadPool.execute(distribuirTarefas);
			
			//informacoes sobre threads
//			Set<Thread> todasAsThreads = Thread.getAllStackTraces().keySet();
//			for (Thread thread : todasAsThreads) {
//				System.out.println(thread.getName());
//			}
//			
//			Runtime runtime = Runtime.getRuntime();
//			int qtdProcessadores = runtime.availableProcessors();
//			System.out.println("Qtd de processadores: " + qtdProcessadores);
		}
		
	}
}
