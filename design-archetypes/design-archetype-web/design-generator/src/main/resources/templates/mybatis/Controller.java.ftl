package ${packageName}.controller;

import ${packageName}.service.${className}Service;
import org.springframework.web.bind.annotation.*;

@RestController
public class ${className}Controller {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

}