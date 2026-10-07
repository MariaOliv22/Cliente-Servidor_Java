import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        // Endereço do servidor.
        String host = "localhost";

        // Portas utilizadas pelo protocolo.
        int portaInscricao = 85;
        int portaSorteio = 88;

        // Lê os dados digitados pelo usuário.
        Scanner teclado = new Scanner(System.in);

        try {

            // ==========================================
            // ETAPA 1: CONEXÃO COM O SERVIDOR NA PORTA 85
            // ==========================================

            Socket socket = new Socket(host, portaInscricao);

            System.out.println("Conectado ao servidor na porta 85!");

            // Canal de saída: envia dados ao servidor.
            PrintWriter saida = new PrintWriter(
                    socket.getOutputStream(), true
            );

            // Canal de entrada: recebe dados do servidor.
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            // 1. Enviar a identificação do participante.
            System.out.print("Digite sua identificação: ");
            String identificacao = teclado.nextLine();

            saida.println(identificacao);

            // 2. Receber a mensagem de boas-vindas.
            String boasVindas = entrada.readLine();
            System.out.println(boasVindas);

            // 3. Receber o prompt solicitando um palpite.
            String prompt = entrada.readLine();
            System.out.println(prompt);

            // 4. Ler e enviar o palpite.
            System.out.print("Digite seu palpite: ");
            int palpite = teclado.nextInt();

            saida.println(palpite);

            // 5. Receber os três resultados do servidor.
            int concorrencia = Integer.parseInt(
                    entrada.readLine()
            );

            double probabilidade = Double.parseDouble(
                    entrada.readLine()
            );

            int numeroSorte = Integer.parseInt(
                    entrada.readLine()
            );

            // Exibir os resultados recebidos.
            System.out.println("\n--- Resultado da inscrição ---");
            System.out.println("Concorrência: " + concorrencia);
            System.out.println("Probabilidade de ganhar: " + probabilidade);
            System.out.println("Seu número da sorte: " + numeroSorte);

            // 6. Encerrar a conexão da porta 85.
            socket.close();

            System.out.println("\nConexão da porta 85 encerrada.");

            // ==========================================
            // ETAPA 2: CONEXÃO COM O SERVIDOR NA PORTA 88
            // ==========================================

            System.out.println("\nAguardando o sorteio...");

            // Criar uma NOVA conexão TCP na porta 88.
            Socket socketSorteio = new Socket(host, portaSorteio);

            // Canal para receber a mensagem final.
            BufferedReader entradaSorteio = new BufferedReader(
                    new InputStreamReader(
                            socketSorteio.getInputStream()
                    )
            );

            // 7. Receber e exibir a mensagem final.
            String mensagemFinal = entradaSorteio.readLine();

            System.out.println("\n--- Resultado do sorteio ---");
            System.out.println(mensagemFinal);

            // 8. Encerrar a conexão da porta 88.
            socketSorteio.close();

            System.out.println("\nConexão da porta 88 encerrada.");

        } catch (IOException e) {

            // Tratar erros de conexão e comunicação.
            System.out.println("Erro de comunicação: " + e.getMessage());

        } catch (NumberFormatException e) {

            // Tratar erros na conversão dos dados recebidos.
            System.out.println("Erro: o servidor enviou um número inválido.");
        }
    }
}