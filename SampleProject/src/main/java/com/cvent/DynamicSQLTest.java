package com.cvent;


import org.apache.ibatis.jdbc.SQL;

/**
 * Created by a.srivastava on 3/9/16.
 */
public class DynamicSQLTest {

    public static void main(String[] args) {
        System.out.println("Query Generated - " + getSqlStmt());
       // System.out.println("Query Generated - " + getSelectQuery1());
    }

    private static String getSelectQuery1() {
        return new SQL() {
            {
                LEFT_OUTER_JOIN("RESPONDENT_ANSWER_DETAIL ON " +
                        "RESPONDENT_ANSWER.respondent_stub = RESPONDENT_ANSWER_DETAIL.respondent_stub" +
                        " AND RESPONDENT_ANSWER.acct_id = RESPONDENT_ANSWER_DETAIL.acct_id" +
                        " AND RESPONDENT_ANSWER.qstn_stub = RESPONDENT_ANSWER_DETAIL.qstn_stub" +
                        " AND RESPONDENT_ANSWER.survey_stub = RESPONDENT_ANSWER_DETAIL.survey_stub");
                SELECT("RESPONDENT_ANSWER.acct_id");
                SELECT("RESPONDENT_ANSWER.respondent_stub");
                SELECT("RESPONDENT_ANSWER.qstn_stub");
                SELECT("RESPONDENT_ANSWER.survey_stub");
                SELECT("RESPONDENT_ANSWER.answer_detail");
                SELECT("RESPONDENT_ANSWER.answer_text");
                SELECT("RESPONDENT_ANSWER.answer_score");
                SELECT("RESPONDENT_ANSWER.score_text");
                SELECT("RESPONDENT_ANSWER.score_detail");
                SELECT("RESPONDENT_ANSWER.answer_count");
                SELECT("RESPONDENT_ANSWER_DETAIL.inner_qstn_text");
                SELECT("RESPONDENT_ANSWER_DETAIL.inner_qstn_choice");
                SELECT("RESPONDENT_ANSWER_DETAIL.answ_text");
                SELECT("RESPONDENT_ANSWER_DETAIL.answ_tag_id");
                SELECT("RESPONDENT_ANSWER_DETAIL.answ_text_sf");
                SELECT("RESPONDENT_ANSWER_DETAIL.answer_count");
                SELECT("RESPONDENT_ANSWER.answer_count");

                FROM("RESPONDENT_ANSWER");


                WHERE("RESPONDENT_ANSWER.acct_id = 1385");
            }
        }.toString();
    }

    private static String getSqlStmt(){
        SQL sqlS = new SQL();
        String expression = "RESPONDENT_ANSWER.acct_id in (\"asdsa\") AND RESPONDENT_ANSWER.survey_stub=\'CA140363-CB17-4E39-925C-005638F04D28\'";
        String sql = new SQL()
                .SELECT("RESPONDENT_ANSWER.acct_id")
                .SELECT("RESPONDENT_ANSWER.respondent_stub")
                .SELECT("RESPONDENT_ANSWER.qstn_stub")
                .SELECT("RESPONDENT_ANSWER.survey_stub")
                .SELECT("RESPONDENT_ANSWER.answer_detail")
                .SELECT("RESPONDENT_ANSWER.answer_text")
                .SELECT("RESPONDENT_ANSWER.answer_score")
                .SELECT("RESPONDENT_ANSWER.score_text")
                .SELECT("RESPONDENT_ANSWER.score_detail")
                .SELECT("RESPONDENT_ANSWER.answer_count")
                .SELECT("RESPONDENT_ANSWER_DETAIL.inner_qstn_text")
                .SELECT("RESPONDENT_ANSWER_DETAIL.inner_qstn_choice")
                .SELECT("RESPONDENT_ANSWER_DETAIL.answ_text")
                .SELECT("RESPONDENT_ANSWER_DETAIL.answ_tag_id")
                .SELECT("RESPONDENT_ANSWER_DETAIL.answ_text_sf")
                .SELECT("RESPONDENT_ANSWER_DETAIL.answer_count")
                .SELECT("RESPONDENT_ANSWER.answer_count")
                .FROM("RESPONDENT_ANSWER")
                .LEFT_OUTER_JOIN("RESPONDENT_ANSWER_DETAIL ON " +
                "RESPONDENT_ANSWER.respondent_stub = RESPONDENT_ANSWER_DETAIL.respondent_stub" +
                " AND RESPONDENT_ANSWER.acct_id = RESPONDENT_ANSWER_DETAIL.acct_id" +
                " AND RESPONDENT_ANSWER.qstn_stub = RESPONDENT_ANSWER_DETAIL.qstn_stub" +
                " AND RESPONDENT_ANSWER.survey_stub = RESPONDENT_ANSWER_DETAIL.survey_stub")
                .WHERE("RESPONDENT_ANSWER.acct_id = 1385")
                .WHERE("RESPONDENT_ANSWER.survey_stub IN ('A','B','C')")
                .WHERE("RESPONDENT_ANSWER.qstn_stub IN ('D','E','F')")
                //.WHERE("")
                .WHERE(expression)
                .ORDER_BY("RESPONDENT_ANSWER.respondent_stub")
                .toString()+"OFFSET 10 ROWS" +
                "FETCH NEXT 20 ROWS ONLY";
        return sql;
    }
    private static String getSelectQuery() {
       /* Table respondent_answer = new Table("RESPONDENT_ANSWER");
        SelectQuery select = new SelectQuery(respondent_answer);
        Table respondent_answer_details = new Table("RESPONDENT_ANSWER_DETAIL");
        select.addJoin(respondent_answer,"respondent_stub",respondent_answer_details,"respondent_stub");
        select.addJoin(respondent_answer,"acct_id",respondent_answer_details,"acct_id");
        select.addJoin(respondent_answer,"qstn_stub",respondent_answer_details,"qstn_stub");
        select.addJoin(respondent_answer,"survey_stub",respondent_answer_details,"survey_stub");

        select.addColumn(respondent_answer, "acct_id");
        select.addColumn(respondent_answer, "respondent_stub");
        select.addColumn(respondent_answer, "qstn_stub");
        select.addColumn(respondent_answer, "survey_stub");

        select.addColumn(respondent_answer, "answer_detail");
        select.addColumn(respondent_answer, "answer_text");
        select.addColumn(respondent_answer, "answer_score");
        select.addColumn(respondent_answer, "score_text");
        select.addColumn(respondent_answer, "score_detail");
        select.addColumn(respondent_answer, "answer_count");

        select.addColumn(respondent_answer_details, "inner_qstn_text");
        select.addColumn(respondent_answer_details, "inner_qstn_choice");
        select.addColumn(respondent_answer_details, "answ_text");
        select.addColumn(respondent_answer_details, "answ_tag_id");

        select.addColumn(respondent_answer_details, "answ_text_sf");
        select.addColumn(respondent_answer_details, "answer_count");

        select.addCriteria(new MatchCriteria(respondent_answer,"acct_id",MatchCriteria.EQUALS,1385));
        return select.toString();*/
        return "";
    }
}
