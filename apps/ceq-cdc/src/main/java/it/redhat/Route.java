package it.redhat;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.builder.Namespaces;
import org.w3c.dom.NodeList;

import java.net.ConnectException;

public class Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("kafka:{{kafka.source.topic.name}}")
        .routeId("From.{{kafka.source.topic.name}}2{{kafka.dest.topic.name}}")
        .log("Received Message From: {{kafka.source.topic.name}}  ${body}")        
        .to("atlasmap:mapping/atlasmap-mapping.adm")
        .log("Send Converted Message to: {{kafka.dest.topic.name}}  ${body}")
        .to("kafka:{{kafka.dest.topic.name}}");

        from("kafka:{{kafka.dest.topic.name}}")
        .routeId("From.{{kafka.dest.topic.name}}")
        .log("Received Message From: {{kafka.dest.topic.name}} ${body}");
    }
}
