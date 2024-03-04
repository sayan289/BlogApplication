package com.blogapplication.blogapplication;

import com.blogapplication.blogapplication.Repository.RoleRepo;
import com.blogapplication.blogapplication.config.AppConstant;
import com.blogapplication.blogapplication.entities.Role;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
@OpenAPIDefinition
@SpringBootApplication
public class BlogapplicationApplication implements CommandLineRunner {
	@Autowired
	RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogapplicationApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper()
	{
		return  new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			Role role=new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ADMIN_USER");
			Role role1=new Role();
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName("NORMAL_USER");
			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
