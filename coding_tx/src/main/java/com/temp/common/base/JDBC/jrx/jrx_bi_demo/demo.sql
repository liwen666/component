
------------------------------------------------------------------
信贷sql

SELECT *,
       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,
       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,
       credit_amount AS credit_amount_group
FROM md_cm_app_case_formatted


SELECT *,
       CASE WHEN approve_rst = 'AR' THEN 1 ELSE 0 END AS is_approved,
       CASE WHEN approve_rst = 'RJ' THEN 1 ELSE 0 END AS is_rejected,
       credit_amount AS credit_amount_group
FROM md_cm_app_case_formatted
WHERE 1 = 1
    $if(hour_gruop)$
        AND biz_hour_group = $hour_gruop$
    $endif$

    $if(province)$
        AND province = $province$
    $endif$

    $if(education)$
        AND education = $education$
    $endif$


    $if(education)$
        AND education = $education$
    $endif$

    $if(age_group)$
        AND age_group = $age_group$
    $endif$

    $if(post_type)$
        AND post_type = $post_type$
    $endif$

-----------------------------------------------------------------------------------
