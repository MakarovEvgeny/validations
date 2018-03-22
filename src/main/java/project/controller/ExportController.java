package project.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import project.model.message.MessageExportRow;
import project.model.validation.ValidationExportRow;
import project.service.ExportService;
import project.view.ValidationExportView;

@Controller
@RequestMapping("export")
@Transactional
public class ExportController {

    @Autowired
    private ExportService service;

    @Autowired
    private ValidationExportView exportExcelView;

    @RequestMapping(method = RequestMethod.GET, path = "excel")
    public ModelAndView exportExcel() {
        List<MessageExportRow> messages = service.getMessages();
        List<ValidationExportRow> validations = service.getValidations();
        return new ModelAndView(exportExcelView, new HashMap<String, Object>(){{
            put("messages", messages);
            put("validations", validations);
        }});
    }
}
