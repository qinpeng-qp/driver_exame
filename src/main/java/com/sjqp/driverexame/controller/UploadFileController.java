package com.sjqp.driverexame.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sjqp.driverexame.entity.ErrorExercise;
import com.sjqp.driverexame.entity.RealExercise;
import com.sjqp.driverexame.entity.SimulatedExercise;
import com.sjqp.driverexame.service.ErrorExerciseService;
import com.sjqp.driverexame.service.RealExerciseService;
import com.sjqp.driverexame.service.SimulatedExerciseService;
import com.sjqp.driverexame.util.StringUtil;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;

/**
 * 上传文件控制层
 *
 * @author qinpeng
 */
@Controller
public class UploadFileController {

    private Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Autowired
    private SimulatedExerciseService simulatedExerciseService;
    @Autowired
    private RealExerciseService realExerciseService;
    @Autowired
    private ErrorExerciseService errorExerciseService;
    @RequestMapping("/uploadFile")
    @ResponseBody
    public JSONObject uploadFile(MultipartFile file, String questionType) throws IOException {
        JSONObject resObj = null;
        try {
            String filePath = "";
            boolean isExcel = false;
            resObj = new JSONObject();
            //上传后的文件名 
            String fileName = file.getOriginalFilename();
            if (StringUtil.isNotEmpty(fileName) && fileName.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel = true;
            }
            InputStream in = file.getInputStream();
            Workbook workbook = null;
            if (isExcel) {
                JSONArray jsonArray = new JSONArray();
                workbook = new XSSFWorkbook(in);
                Sheet sheet = workbook.getSheetAt(0);
                if (Objects.nonNull(sheet)) {
                    resObj.put("msg", "上传成功");
                }

                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    try {
                        Row row = sheet.getRow(i);
                        if (Objects.isNull(row)) {
                            continue;
                        }
                        //设置为字符串类型
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                        row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);

                        String comment = row.getCell(0).getStringCellValue();
                        String questionA = row.getCell(1).getStringCellValue();
                        String questionB = row.getCell(2).getStringCellValue();
                        String questionC = row.getCell(3).getStringCellValue();
                        String questionD = row.getCell(4).getStringCellValue();
                        String answer = row.getCell(5).getStringCellValue();

                        JSONObject questionJson = new JSONObject();
                        questionJson.put("A", questionA);
                        questionJson.put("B", questionB);
                        questionJson.put("C", questionC);
                        questionJson.put("D", questionD);

                        //封装单个json对象
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("comment", comment);
                        jsonObject.put("choice", questionJson);
                        jsonObject.put("answer", answer);
                        logger.info(jsonObject.toString());
                        //添加到json数组中
                        jsonArray.add(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                logger.info(jsonArray.toString());
                //判断上传题目类型,按照类型入库
                switch (Integer.parseInt(questionType)) {
                    case 0:
                        List<RealExercise> realExerciseList = JSONObject.parseArray(jsonArray.toJSONString(),RealExercise.class);
                        realExerciseService.saveRealExercise(realExerciseList);
                        break;
                    case 1:
                        List<ErrorExercise> errorExercises = JSONObject.parseArray(jsonArray.toJSONString(),ErrorExercise.class);
                        errorExerciseService.saveErrorExercise(errorExercises);
                        break;
                    case 2:
                        List<SimulatedExercise> simulatedExerciseList = JSONObject.parseArray(jsonArray.toJSONString(),SimulatedExercise.class);
                        simulatedExerciseService.saveSimulatedExercise(simulatedExerciseList);
                        break;
                    default:
                        break;
                }
            } else {
                resObj.put("msg", "文件格式错误");
            }
        } catch (Exception e) {
            logger.error("批量上传异常 e{}",e);
            resObj.put("msg","系统异常");
        }
        return resObj;

    }

}
