package br.edu.ifc.pubsub;

import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.Queue;
import org.apache.qpid.jms.JmsConnectionFactory;

/** Experimento: mensagens persistentes sobrevivem à desconexão do consumidor. */
public class ProdutorPersistente {
    public static void main(String[] args) {
        try (JMSContext contexto = new JmsConnectionFactory("amqp://localhost:61616")
                .createContext("admin", "admin")) {
            Queue fila = contexto.createQueue("fila.persistente");
            contexto.createProducer()
                    .setDeliveryMode(DeliveryMode.PERSISTENT)
                    .send(fila, "Mensagem persistente de experimento");
            System.out.println("Mensagem persistente enviada para " + fila);
        } catch (JMSRuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
