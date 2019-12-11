UPDATE app_version SET update_information=CONCAT(update_information,'name') WHERE version_code ='20190706'

更新数据库的数据不需要把数据查出来然后去更新，可以


<update id="batchUpdateLoginTime" parameterType="pubUserEmployee">
    UPDATE pub_user_employee SET startHour=#{startHour},startMinute=#{startMinute},
        endHour=#{endHour},endMinute=#{endMinute},loginCycle=#{loginCycle} 
    WHERE sysEmpId IN (
    <foreach item="p" collection="sysEmpIds" separator=",">
        #{p}
    </foreach>
    ) AND comId = #{comId};
</update>