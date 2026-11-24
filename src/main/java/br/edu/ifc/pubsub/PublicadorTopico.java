package br.edu.ifc.pubsub;

import jakarta.jms.JMSContext;
import jakarta.jms.JMSRuntimeException;
import jakarta.jms.Topic;
import org.apache.qpid.jms.JmsConnectionFactory;

/** Publica uma mensagem em um tópico AMQP para todos os assinantes ativos. */
public class PublicadorTopico {
    public static void main(String[] args) {
        String servidor = "amqp://localhost:61616";

        try (JMSContext contexto = new JmsConnectionFactory(servidor)
                .createContext("admin", "admin")) {
            Topic topico = contexto.createTopic("topico.eventos");
            String mensagem = args.length > 0 ? String.join(" ", args)
                    : "Evento publicado no tópico";
            contexto.createProducer().send(topico, mensagem);
            System.out.println("Mensagem publicada: " + mensagem);
        } catch (JMSRuntimeException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
