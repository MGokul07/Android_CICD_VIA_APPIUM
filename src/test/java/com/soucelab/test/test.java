package com.soucelab.test;

public class test {

	public static void main(String[] args) {
		// highest to lowest use " < "
		// lowest to highest use " > "

		int a[]={44,66,99,77,33,22,55};
		int temp;  
		for (int i = 0; i < a.length; i++)   
		        {  
		            for (int j = i + 1; j < a.length; j++)   
		            {  
		                if (a[i] < a[j])   
		                {  
		                    temp = a[i];  
		                    a[i] = a[j];  
		                    a[j] = temp;  
		                }  
		            }  
		        }  
		
		System.out.println("second largest value : "+a[1]);
	}

}
