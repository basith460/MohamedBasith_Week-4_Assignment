package com.gl.controller;


import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.gl.model.Employee;

@Controller
public class MyController {
	
	@RequestMapping("/")
	public String welcome() {
		return "index";
	}
	
	//
	
	@RequestMapping("/employeepage")
	public String empPage(Model data) {
		SessionFactory factory=new Configuration().configure().buildSessionFactory();

		Session session=factory.openSession();

		try {

			Transaction tx=session.beginTransaction();

		Query q1=session.createQuery("from Employee");
		List<Employee>  employees=q1.getResultList();
		data.addAttribute("employee",employees);
		tx.commit();

		}catch(Exception ex) {
			System.out.println("Hibernate error "+ex.getMessage());
		}
		return "employee-list";
	}
	
	@RequestMapping("/employeeadd")
	public String addEmployee() {
		

		return "employee-add";
	}
	
	@PostMapping("/showemployee")
	public String showEmp(@RequestParam String empname,@RequestParam String empaddress,@RequestParam String empphone,@RequestParam String empsalary,Model data) {
		
		SessionFactory factory=new Configuration().configure().buildSessionFactory();

		Session session=factory.openSession();

		try {

			Transaction tx=session.beginTransaction();

			//all the db code/work

			Employee e1=new Employee(empname, empaddress, empphone, empsalary);
			session.save(e1);
			
			Query q1=session.createQuery("from Employee");
			List<Employee>  employees=q1.getResultList();
			data.addAttribute("employee",employees);

			tx.commit();

		}catch(Exception ex) {
			System.out.println("Hibernate error "+ex.getMessage());
		}
		
		return "employee-list";
	}
	
	@GetMapping("/update-employee-form")
	public String updateEmployeeForom(@RequestParam int id,Model data) {

		SessionFactory factory=new Configuration().configure().buildSessionFactory();

		Session session=factory.openSession();


		try {

			Employee updateEmployee=session.get(Employee.class, id);

			data.addAttribute("employee",updateEmployee);


		}catch(Exception ex) {
			System.out.println("Hibernate error "+ex.getMessage());
		}

		return "update-employee";
	}
	
	@PostMapping("/updatesave")
	public String saveUpdate(@RequestParam int id,@RequestParam String empname,@RequestParam String empaddress,@RequestParam String empphone,@RequestParam String empsalary,Model data) {
		
		SessionFactory factory=new Configuration().configure().buildSessionFactory();

		Session session=factory.openSession();

		try {

			Transaction tx=session.beginTransaction();

			//all the db code/work

			Employee e1=new Employee(id,empname, empaddress, empphone, empsalary);
			session.update(e1);
			
			Query q1=session.createQuery("from Employee");
			List<Employee>  employees=q1.getResultList();
			data.addAttribute("employee",employees);

			tx.commit();

		}catch(Exception ex) {
			System.out.println("Hibernate error "+ex.getMessage());
		}
		
		return "employee-list";
	}
	
	
	@GetMapping("/delete-employee-form")
	public String delEmployee(@RequestParam int id,Model data) {
		SessionFactory factory=new Configuration().configure().buildSessionFactory();

		Session session=factory.openSession();


		try {
			
			Transaction tx=session.beginTransaction();
			Employee empdel=new Employee(id,"","","","");
			session.delete(empdel);
			

			Query q1=session.createQuery("from Employee");
			List<Employee>  employees=q1.getResultList();
			data.addAttribute("employee",employees);

			
	        tx.commit();

		}catch(Exception ex) {
			System.out.println("Hibernate error "+ex.getMessage());
		}
		
		return "employee-list";
	}
	
}
