package mvc.moiso.security;


import mvc.moiso.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter{
    @Autowired
    private DataSource dataSource;

    @Autowired
    Handler customSuccessHandler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select correo,password,estado from empleados where correo=?")
                .authoritiesByUsernameQuery("select correo, rol from empleados where correo=?");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","VerEmpresas/**").hasRole("ADMIN")
                .antMatchers("/VerEmpleados/**").hasRole("ADMIN")
                .antMatchers("/Empresa/**").hasRole("ADMIN")
                .antMatchers("/Empleado/**").hasRole("ADMIN")
                .antMatchers("/VerMovimientos/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/AgregarMovimiento/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/AgregarEmpleado/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/EditarMovimiento/**").hasAnyRole("ADMIN","USER")
                .and().formLogin().successHandler(customSuccessHandler)
                .and().exceptionHandling().accessDeniedPage("/Denegado")
                .and().logout().permitAll();
    }

}
