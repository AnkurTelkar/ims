package com.ims.printing.service;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
public class PrintServiceTest
{
    public static void main( String[] args )
        throws Exception
    {
        /*  first, get and initialize an engine  */

    	Template template = Velocity.getTemplate( "/com/ims/printing/file/receipt.txt" );
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        /*  next, get the Template  */
        Template t = ve.getTemplate( "c:/testVM.txt" );
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("name", "World");
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        /* show the World */
        System.out.println( writer.toString() );     
    }
}