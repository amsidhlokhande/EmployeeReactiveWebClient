package com.amsidh.mvc.handler;

import java.time.Duration;

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

	private static final String EMPLOYEES_BASE_URI = "/employees";
	private static final String EMPLOYEES_BASE_URI_WITH_ID = "/employees/{id}";
	WebClient webClient = WebClient.create("http://localhost:8989");

	public Mono<ServerResponse> getEmployees(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler getEmployees called");
		Flux<Employee> employeeFlux = webClient.get().uri(EMPLOYEES_BASE_URI).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToFlux(Employee.class).delayElements(Duration.ofSeconds(1)).log();
		return ServerResponse.ok().body(employeeFlux, Employee.class);

	}

	public Mono<ServerResponse> getEmployeesUsingExchange(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler getEmployeesUsingExchange called");
		Flux<Employee> employeeFlux = webClient.get().uri(EMPLOYEES_BASE_URI).accept(MediaType.APPLICATION_JSON)
				.exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Employee.class));
		return ServerResponse.ok().body(employeeFlux, Employee.class);

	}

	public Mono<ServerResponse> getEmployeeById(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler getEmployees called");
		Mono<Employee> employeeMono = webClient.get()
				.uri(EMPLOYEES_BASE_URI_WITH_ID, Integer.parseInt(serverRequest.pathVariable("id")))
				.accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Employee.class).log();
		return ServerResponse.ok().body(employeeMono, Employee.class);

	}

	public Mono<ServerResponse> saveEmployee(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler saveEmployee called");
		Mono<Employee> employeeMono = webClient.post().uri(EMPLOYEES_BASE_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				//.body(BodyInserters.fromProducer(serverRequest.bodyToMono(Employee.class), Employee.class)).retrieve() //This will also works
				.body(serverRequest.bodyToMono(Employee.class), Employee.class).retrieve()
				.bodyToMono(Employee.class).log();
		return ServerResponse.ok().body(employeeMono, Employee.class);

	}

	public Mono<ServerResponse> updateEmployee(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler updateEmployee called");
		Mono<Employee> employeeMono = webClient.patch()
				.uri(EMPLOYEES_BASE_URI_WITH_ID, Integer.parseInt(serverRequest.pathVariable("id")))
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromProducer(serverRequest.bodyToMono(Employee.class), Employee.class)).retrieve()
				.bodyToMono(Employee.class).log();
		return ServerResponse.ok().body(employeeMono, Employee.class);

	}

	public Mono<ServerResponse> deleteEmployee(ServerRequest serverRequest) {
		log.info("EmployeeClientHandler deleteEmployee called");
		Mono<Void> employeeMono = webClient.delete()
				.uri(EMPLOYEES_BASE_URI_WITH_ID, Integer.parseInt(serverRequest.pathVariable("id"))).retrieve()
				.bodyToMono(Void.class).log();
		return ServerResponse.ok().body(employeeMono, Void.class);

	}
}
