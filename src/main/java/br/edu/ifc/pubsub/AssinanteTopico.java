
package br.edu.ifc.pubsub;

import jakarta.jms.*;
import org.apache.qpid.jms.JmsConnectionFactory;
import java.time.LocalTime;

public class AssinanteTopico {

    public static void main(String[] args) {

        String servidor = "amqp://localhost:61616";

        JmsConnectionFactory fabrica =
                new JmsConnectionFactory(servidor);

        try (JMSContext contexto =
                     fabrica.createContext("admin", "admin")) {

            Topic topico = contexto.createTopic("topico.eventos.teste2");

            JMSConsumer consumidor = contexto.createConsumer(topico);

            long pid = ProcessHandle.current().pid();

            System.out.println("Assinante PID: " + pid);
            System.out.println("Aguardando mensagens do topico...");

            for (int i = 1; i <= 5; i++) {

                String mensagem = consumidor.receiveBody(String.class);

                System.out.println(
                        "PID: " + pid +
                                " | Recebida: " + mensagem +
                                " | Horario: " + LocalTime.now()
                );
            }

            System.out.println("Assinante " + pid + " finalizado!");

        } catch (JMSRuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
