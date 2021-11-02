package vehicle_survey_springboot.New.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehicle_survey_springboot.New.entity.Employee;
import vehicle_survey_springboot.New.mapper.EmployeeMapper;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public HashMap login(String no, String password) {
        int code;
        String msg;
        Object data;
        Employee employee = new Employee();
        HashMap map = new HashMap();
        employee = employeeMapper.login(no, password);
        if(employee!=null){
            if (employee.getStatus()!=-1)   {
                map.put("employee",employee);
                map.put("msg","登录成功");
            }

        }else {
            map.put("msg","工号或密码错误");
        }
        return map;
    }

    public int updatePwd(String no,String oldPassword,String newPassword){
        String msg = (String) login(no, oldPassword).get("msg");
        if ("工号或密码错误".equals(msg)) {
            return -1;
        } else if("登录成功".equals(msg)){
            return employeeMapper.updatePwd(no,oldPassword,newPassword);
        }
        return 0;
    }

    public ArrayList<Employee> getEmployee_position(String pos){
        return employeeMapper.getEmployee_position(pos);
    }

    public ArrayList<Employee> getEmployee_no(String no){
        return employeeMapper.getEmployee_no(no);
    }

}
