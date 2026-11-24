
package br.edu.ifc.pubsub;

import jakarta.jms.*;
import org.apache.qpid.jms.JmsConnectionFactory;
import java.time.LocalTime;

public class PublicadorTopico {

    public static void main(String[] args) throws InterruptedException {

        JmsConnectionFactory fabrica =
                new JmsConnectionFactory("amqp://localhost:61616");

        try (JMSContext contexto =
                     fabrica.createContext("admin", "admin")) {

            Topic topico = contexto.createTopic("topico.eventos.teste2");

            JMSProducer produtor = contexto.createProducer();
            produtor.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 1; i <= 5; i++) {

                String mensagem = "Evento numero " + i;

                produtor.send(topico, mensagem);

                System.out.println(
                        "Publicado: " + mensagem +
                                " | Horario: " + LocalTime.now()
                );

                if (i < 5) {
                    Thread.sleep(60000);
                }
            }

            System.out.println("Publicacoes finalizadas!");

        } catch (JMSRuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
