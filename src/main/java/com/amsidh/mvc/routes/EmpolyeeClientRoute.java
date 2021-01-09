package com.amsidh.mvc.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.handler.EmployeeClientHandler;

@Configuration
public class EmpolyeeClientRoute {

	
	@Bean
	public RouterFunction<ServerResponse> getEmployeeRoutes(EmployeeClientHandler employeeClientHandler) {
		return RouterFunctions.route(RequestPredicates.GET("/webclient/employees"), employeeClientHandler::getEmployees)
				.and(RouterFunctions.route(RequestPredicates.GET("/webclient/employees/flux"), employeeClientHandler::getEmployeesUsingExchange))
				.and(RouterFunctions.route(RequestPredicates.GET("/webclient/employees/{id}"), employeeClientHandler::getEmployeeById))
				.and(RouterFunctions.route(RequestPredicates.POST("/webclient/employees"), employeeClientHandler::saveEmployee))
				.and(RouterFunctions.route(RequestPredicates.PATCH("/webclient/employees/{id}"), employeeClientHandler::updateEmployee))
				.and(RouterFunctions.route(RequestPredicates.DELETE("/webclient/employees/{id}"), employeeClientHandler::deleteEmployee))
				;
	}
}
