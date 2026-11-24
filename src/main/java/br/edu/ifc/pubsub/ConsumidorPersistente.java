package br.edu.ifc.pubsub;

import jakarta.jms.JMSContext;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.Queue;
import org.apache.qpid.jms.JmsConnectionFactory;

/** Consome a fila persistente e confirma o recebimento ao fechar o contexto. */
public class ConsumidorPersistente {
    public static void main(String[] args) {
        try (JMSContext contexto = new JmsConnectionFactory("amqp://localhost:61616")
                .createContext("admin", "admin")) {
            Queue fila = contexto.createQueue("fila.persistente");
            String mensagem = contexto.createConsumer(fila).receiveBody(String.class, 10000);
            System.out.println(mensagem == null ? "Nenhuma mensagem recebida" :
                    "Mensagem persistente recebida: " + mensagem);
        } catch (JMSRuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
