package com.bignerdranch.android.testonline;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public static void main(String[] args){

        Connection con = JDBCUtils.getConn();
        JDBCUtils.close(con);
    }
}