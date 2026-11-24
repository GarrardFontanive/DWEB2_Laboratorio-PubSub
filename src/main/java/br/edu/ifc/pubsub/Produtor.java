
package br.edu.ifc.pubsub;

import jakarta.jms.*;
import org.apache.qpid.jms.JmsConnectionFactory;

public class Produtor {

    public static void main(String[] args) throws InterruptedException {

        String servidor = "amqp://localhost:61616";

        JmsConnectionFactory fabrica =
                new JmsConnectionFactory(servidor);

        try (JMSContext contexto =
                fabrica.createContext("admin", "admin")) {

            Queue fila = contexto.createQueue("fila.teste5");

            for (int i = 1; i <= 5 ; i++) {
                String mensaagem = "Mensagem numero " + i;

                contexto.createProducer().send(fila, mensaagem);

                System.out.println("Mensagem enviada " + mensaagem);

                if (i < 5) {
                    Thread.sleep(60000);
                }
            }

            System.out.println("Todas as mensagens foram enviadas com sucesso");

        } catch (JMSRuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
