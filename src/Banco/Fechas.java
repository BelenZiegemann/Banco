package Banco;

import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.lang.*;

public class Fechas
{
	/*
	 java.util.Date --> dia mes nroDia hora año
	 java.sql.Date --> año-mes-dia
	*/
	
   public static java.util.Date convertirStringADate(String p_fecha)
   {
      java.util.Date retorno = null;
      if (p_fecha != null)
      {
         try
         {
            retorno = (new SimpleDateFormat("dd/MM/yyyy")).parse(p_fecha);
         }
         catch (ParseException ex)
         {
            //System.out.println(ex.getMessage());
         }
      }
      return retorno;
   }

   public static String convertirDateAString(java.util.Date p_fecha)
   {
      String retorno = null;
      if (p_fecha != null)
      {
         retorno = (new SimpleDateFormat("dd/MM/yyyy")).format(p_fecha);
      }
      return retorno;
   }

   public static String convertirDateAStringDB(java.util.Date p_fecha)
   {
      String retorno = null;
      if (p_fecha != null)
      {
         retorno = (new SimpleDateFormat("yyyy-MM-dd")).format(p_fecha);
      }
      return retorno;
   }

   public static java.sql.Date convertirDateADateSQL(java.util.Date p_fecha)
   {
      java.sql.Date retorno = null;
      if (p_fecha != null)
      {
         retorno = java.sql.Date.valueOf((new SimpleDateFormat("yyyy-MM-dd")).format(p_fecha));
      }
      return retorno;
   }

   public static java.sql.Date convertirStringADateSQL(String p_fecha)
   {
      java.sql.Date retorno = null;
      if (p_fecha != null)
      {
         retorno = Fechas.convertirDateADateSQL(Fechas.convertirStringADate(p_fecha));
      }
      return retorno;
   }

   public static boolean validar(String p_fecha)
   {
      if (p_fecha != null)
      {
         try
         {
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	sdf.setLenient(false);
        	sdf.parse(p_fecha);
            return true;
         }
         catch (ParseException ex) 
         {
        	 
         }
      }
      return false;
   }
   
   private boolean esBisiesto(int year) 
   {
	    return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
   }
   
   public void validarFecha(String fecha) {
	 //fecha con formato dd/mm/aaaa
	   int flag=0;
	   int dia = 0, mes = 0, anio = 0;
	   
	   try 
	   {
		   //dia mes y anio como ints
		   while(fecha.charAt(flag)!='/') 
		   {
			   dia = dia*10 + Integer.parseInt(fecha.charAt(flag)+"");
			   flag++;
		   }
		   flag++;
		   while(fecha.charAt(flag)!='/') 
		   {
			   mes = mes*10 + Integer.parseInt(fecha.charAt(flag)+"");
			   flag++;
		   }
		   flag++;
		   while(flag < fecha.length()) 
		   {
			   anio = anio*10 + Integer.parseInt(fecha.charAt(flag)+"");
			   flag++;
		   }
		   
		   boolean cumple= (mes>0 && mes<13);
		   
		   if(mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10 || mes==12) 
		   {
			   if(dia>31 || dia<0) 
			   {
				   cumple=false;
			   }
		   }
		   else 
		   {
			   if(mes==4 || mes==6 || mes==9 || mes==11) 
			   {
				   if(dia>30 || dia<0)
				   {
					   cumple=false;
				   }
			   }
			   else 
			   {
				   if( (dia>28 && !esBisiesto(anio)) || (dia>29 && esBisiesto(anio)) || dia<0)
				   {
					   cumple=false;
				   }
			   }
		   }
		   if(!cumple)
		   {
			   flag=500;
			   fecha.charAt(flag);
		   }
	  }
	   catch(Exception excp) 
	   {
		   flag=500;
		   fecha.charAt(flag);
	   }
   }

}
