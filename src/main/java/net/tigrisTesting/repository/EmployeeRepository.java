package net.tigrisTesting.repository;

import net.tigrisTesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    public Employee findByEmail(String email);


    // jpql index paramaters
    @Query("select e from Employee e where e.firstName= ?1 and e.lastName= ?2")
    public Employee findByJPQL(String firstName,String lastName);

    // jpql name paramaters
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    public Employee findByJPQLParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    //native query index paramters
    @Query(value = "select * from employee e  where e.first_name=?1 and e.last_name=?2",nativeQuery = true)
    public Employee findByNativeSql(String firstName,String lastName);

    // native query name paramaters
    @Query(value = "select * from employee e where e.first_name =:firstName and e.last_name =:lastName",nativeQuery = true)
    public Employee findByNativeNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
