package com.amsidh.mvc.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.model.Employee;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EmployeeClientHandler {

	WebClient webClient = WebClient.create("http://localhost:8989");

	public Mono<ServerResponse> getEmployees(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler getEmployees called");
		Flux<Employee> employeeFlux = webClient.get().uri("/employees").accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToFlux(Employee.class);
		return ServerResponse.ok().body(employeeFlux, Employee.class);

	}

	public Mono<ServerResponse> getEmployeeById(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler getEmployees called");
		Mono<Employee> employeeMono = webClient.get()
				.uri(String.format("/employees/%s", Integer.parseInt(serverRequest.pathVariable("id"))))
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Employee.class);
		return ServerResponse.ok().body(employeeMono, Employee.class);

	}

	public Mono<ServerResponse> saveEmployee(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler saveEmployee called");
		Mono<Employee> employeeMono = webClient.post()
				.uri("/employees")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromProducer(serverRequest.bodyToMono(Employee.class), Employee.class)).retrieve()
				.bodyToMono(Employee.class);
		return ServerResponse.ok().body(employeeMono, Employee.class);

	}

	
	public Mono<ServerResponse> updateEmployee(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler updateEmployee called");
		Mono<Employee> employeeMono = webClient.patch()
				.uri(String.format("/employees/%s", Integer.parseInt(serverRequest.pathVariable("id"))))
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromProducer(serverRequest.bodyToMono(Employee.class), Employee.class)).retrieve()
				.bodyToMono(Employee.class);
		return ServerResponse.ok().body(employeeMono, Employee.class);

	}

	public Mono<ServerResponse> deleteEmployee(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler deleteEmployee called");
		Mono<Void> employeeMono = webClient.delete()
				.uri(String.format("/employees/%s", Integer.parseInt(serverRequest.pathVariable("id")))).retrieve()
				.bodyToMono(Void.class);
		return ServerResponse.ok().body(employeeMono, Void.class);

	}
}
