package br.com.senac.api.scheduler.exercicioListagem;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ListagemJobTriggerBuild {

    @Bean
    public JobDetail jobDetailListagem(){

        return JobBuilder.newJob(ListagemJob.class)
                .withIdentity("ListagemJob", "grupoListagem")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerListagem(){

        return TriggerBuilder.newTrigger()
                .withIdentity("ListagemTrigger", "grupoListagem")
                .forJob(this.jobDetailListagem())
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * * *"))
                .build();

    }

}
