
package br.edu.ifc.pubsub;

import jakarta.jms.*;
import org.apache.qpid.jms.JmsConnectionFactory;

public class Consumidor {

    public static void main(String[] args) {

        String servidor = "amqp://localhost:61616";

        JmsConnectionFactory fabrica =
                new JmsConnectionFactory(servidor);

        try (JMSContext contexto =
                fabrica.createContext("admin", "admin")) {

            Queue fila = contexto.createQueue("fila.teste5");

            JMSConsumer consumidor = contexto.createConsumer(fila);

            System.out.println("Consumidor PID: " + ProcessHandle.current().pid());

            System.out.println("Aguardando mensagem...");

            String mensagem = consumidor.receiveBody(String.class);

            if (mensagem != null) {
                System.out.println("Mensagem recebida: " + mensagem);
            } else {
                System.out.println("Nenhuma mensagem recebida em 10 segundos.");
            }

        } catch (JMSRuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
