package vehicle_survey_springboot.New.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import vehicle_survey_springboot.New.entity.Employee;

import java.util.ArrayList;

@Mapper
public interface EmployeeMapper {

    @Select("select * from test_employee where no = #{no} && password = #{password}")
    public Employee login(@Param("no")String no,@Param("password")String password);

    @Update("update test_employee set password = #{newPassword} where no = #{no}")
    public int updatePwd(@Param("no")String no,@Param("oldPassword")String oldPassword,@Param("newPassword")String newPassword);

    @Select("select * from test_employee where position = #{pos} && status = 1")
    public ArrayList<Employee> getEmployee_position(@Param("pos") String pos);

    @Select("select * from test_employee where no = #{no} && status = 1")
    public ArrayList<Employee> getEmployee_no(@Param("no") String no);
}
