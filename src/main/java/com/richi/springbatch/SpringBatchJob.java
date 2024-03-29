package com.richi.springbatch;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;

public class SpringBatchJob {

	private String jobName;
	private JobLocator jobLocator;
	private JobLauncher jobLauncher;
	private File contentDirectory;
	
	// Mover carpetas a USB E:/ 
	//private String directoryPath = "C:/Users/RichiDocuments/workspace-sts-3.8.3.RELEASE/spring-batch-quartz-scheduler/csv/inputFiles/";
	private String directoryPath = "E:/inputFiles";
	
	
	public void init(){
		contentDirectory = new File(directoryPath);
	}
	
	boolean fileFound = false;
	
	/** El m�todo performJob ser� llamado por el programador peri�dicamente. 
	 * En cada ejecuci�n, comprobamos un directorio de entrada para los archivos de entrada. 
	 * 
	 * Si los archivos est�n presentes, 
	 * creamos un mapa que contiene el nombre del archivo real m�s una fecha (para hacerlo �nico entre los par�metros del trabajo), 
	 * y pasar este mapa como una entrada a JobParameters seguido de iniciar el trabajo con JobLauncher. 
	 * 
	 * Este mapa nos referiremos en el contexto de Spring para crear una referencia al recurso de archivo real.
	 * */
	public void performJob() {

		System.out.println("---------------- CLASS SpringBatchJob performJob() ---------------------------------------------------");
		System.out.println("class SpringBatchJob: Starting ExamResult Job");
		
		try{
		
			if(contentDirectory== null || !contentDirectory.isDirectory()){
				System.err.println("class SpringBatchJob: Input directory doesn't exist. Job ExamResult terminated");
			}
			
			fileFound = false;
			
			for(File file : contentDirectory.listFiles()){
				if(file.isFile()){
					System.out.println("class SpringBatchJob: File found :"+file.getAbsolutePath());
					fileFound = true;
					
					JobParameter param = new JobParameter(file.getAbsolutePath());
					Map<String, JobParameter> map = new HashMap<String, JobParameter>();
					map.put("examResultInputFile", param);
					map.put("date", new JobParameter(new Date()));
				
				
					JobExecution result = jobLauncher.run(jobLocator.getJob(jobName), new JobParameters(map)); 
					System.out.println("class SpringBatchJob: ExamResult Job completetion details : "+result.toString());
				}
			}
			if(!fileFound){
				System.out.println("class SpringBatchJob: No Input file found, Job terminated.");
			}
		} catch(JobExecutionException ex){
			System.out.println("class SpringBatchJob: ExamResult Job halted with following exception :" + ex);
		}
		
	}

	/** ============================ Asociadas al BEAN ===============================
	
	<bean id="springBatchJob" class="com.richi.springbatch.SpringBatchJob">
		<property name="jobName" value="examResultBatchJob" />
		<property name="jobLocator" ref="jobRegistry" />
		<property name="jobLauncher" ref="jobLauncher" />
	</bean>
	*/	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
	
	
}
