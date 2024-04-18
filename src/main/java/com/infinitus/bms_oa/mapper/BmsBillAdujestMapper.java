package com.infinitus.bms_oa.mapper;


import com.infinitus.bms_oa.pojo.BmsBillAdjust;
import com.infinitus.bms_oa.pojo.Infinitus;
import com.infinitus.bms_oa.pojo.PlatCode;
import org.apache.ibatis.annotations.Mapper;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface BmsBillAdujestMapper {
    BmsBillAdjust selectAdujestById(Integer id);

    Infinitus getInfinitusBill(Integer id);

    String selectLoginNameById(String id);

    List<Map<Object,String>> selectIdByBillFlag();

    List<BmsBillAdjust> selectBillByBillFlag();

    List<BmsBillAdjust> selectExceptionByBillFlag();

    Boolean updateOA_flag(String oa_flag,String id);

    Boolean updateOA_flag_ex(String oa_flag,String id);

    Boolean updateBillStatusDate(String adj_no, Date approval_dt);

    Boolean updateExceptionStatusDate( String adj_no, Date approval_dt);

    String getOwnerNameByKey(String owner_key);

    String getPlatCodeName(String code);

    PlatCode getPlatCode(String code);

    String getJscName(String wh_code);

    String getGysName(String vendor_code);

    String getCwkmName(String account_code);

    List<BmsBillAdjust> getBillListDetail(String code);

    List<BmsBillAdjust> getBillListDetailView(String code,String submit_id);

    String getBillListDetail_JSNY(String code,String submit_id);

    boolean updateOaFlag_all(Integer oa_flag,String code);

    boolean updateAdjLogCode(String code, String create_id);
    boolean updateExceptionLogCode(String code, String create_id);
    boolean updateExemptLogCode(String code, String create_id);


    boolean updateOaFlagEx_all(Integer oa_flag, String code);

    boolean updateOaFlagExExempt_all(Integer oa_flag, String code);

    boolean updateStatusAndApeDate(String Code,Date approval_dt,String status);

    boolean updateExceptionStatusAndApeDate(String Code,Date approval_dt,String status);

    boolean updateExceptionExemptStatusAndApeDate(String Code,Date approval_dt,String status);
}
