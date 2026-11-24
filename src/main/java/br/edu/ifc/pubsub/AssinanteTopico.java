package br.edu.ifc.pubsub;

import jakarta.jms.JMSContext;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.Topic;
import org.apache.qpid.jms.JmsConnectionFactory;

/** Recebe mensagens do tópico enquanto o processo estiver ativo. */
public class AssinanteTopico {
    public static void main(String[] args) {
        String servidor = "amqp://localhost:61616";

        try (JMSContext contexto = new JmsConnectionFactory(servidor)
                .createContext("admin", "admin")) {
            Topic topico = contexto.createTopic("topico.eventos");
            JMSConsumer consumidor = contexto.createConsumer(topico);
            System.out.println("Aguardando eventos em " + topico);
            while (true) {
                String mensagem = consumidor.receiveBody(String.class);
                if (mensagem != null) {
                    System.out.println("Evento recebido: " + mensagem);
                }
            }
        } catch (JMSRuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
