import java.io.*;
import java.net.*;

public class ServidorTeste {

    public static void main(String[] args) {

        int portaInscricao = 85;
        int portaSorteio = 88;

        try {

            // =================================================
            // SERVIDOR DA PORTA 85
            // =================================================

            ServerSocket servidor85 = new ServerSocket(portaInscricao);

            System.out.println("Servidor iniciado na porta 85.");
            System.out.println("Aguardando cliente...");

            Socket cliente = servidor85.accept();

            System.out.println("Cliente conectado na porta 85.");

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(
                            cliente.getInputStream()
                    )
            );

            PrintWriter saida = new PrintWriter(
                    cliente.getOutputStream(),
                    true
            );

            // Receber identificação
            String identificacao = entrada.readLine();

            System.out.println("Identificação recebida: " + identificacao);

            // Enviar boas-vindas
            saida.println("Bem-vindo ao sorteio Em busca do doce!");

            // Solicitar palpite
            saida.println("Digite seu palpite:");

            // Receber palpite
            int palpite = Integer.parseInt(
                    entrada.readLine()
            );

            System.out.println("Palpite recebido: " + palpite);

            // Enviar os três resultados
            saida.println(120);       // concorrência
            saida.println(0.0083);    // probabilidade
            saida.println(42);        // número da sorte

            System.out.println("Dados enviados ao cliente.");

            // Fechar conexão da porta 85
            cliente.close();
            servidor85.close();

            System.out.println("Conexão da porta 85 encerrada.");


            // =================================================
            // SERVIDOR DA PORTA 88
            // =================================================

            ServerSocket servidor88 = new ServerSocket(portaSorteio);

            System.out.println("\nServidor iniciado na porta 88.");

            System.out.println("Aguardando conexão para o sorteio...");

            Socket clienteSorteio = servidor88.accept();

            System.out.println("Cliente conectado na porta 88.");

            PrintWriter saidaSorteio = new PrintWriter(
                    clienteSorteio.getOutputStream(),
                    true
            );

            // Enviar mensagem final
            saidaSorteio.println("Resultado do sorteio: voce ganhou o doce!");

            // Fechar conexão
            clienteSorteio.close();
            servidor88.close();

            System.out.println("Servidor encerrado.");

        } catch (IOException e) {

            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}