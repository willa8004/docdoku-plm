/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2013 DocDoku SARL
 *
 * This file is part of DocDokuPLM.
 *
 * DocDokuPLM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDokuPLM is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
 * GNU Affero General Public License for more details.  
 *  
 * You should have received a copy of the GNU Affero General Public License  
 * along with DocDokuPLM.  If not, see <http://www.gnu.org/licenses/>.  
 */

package com.docdoku.core.workflow;

import java.util.*;
import javax.persistence.*;

/**
 * <a href="ParallelActivity.html">ParallelActivity</a> is a kind of activity where 
 * all its tasks start at the same time as the activity itself.
 * Thus, there is no order between the executions of tasks.
 * The <code>tasksToComplete</code> attribute specifies the number of tasks that
 * should be completed so the workflow can progress to the next step. 
 * 
 * @author Florent Garin
 * @version 1.0, 02/06/08
 * @since   V1.0
 */
@Table(name="PARALLELACTIVITY")
@Entity
public class ParallelActivity extends Activity {


    private int tasksToComplete;
    
    public ParallelActivity() {

    }

    public ParallelActivity(int pStep, String pLifeCycleState, int pTasksToComplete) {
        super(pStep, pLifeCycleState);
        tasksToComplete=pTasksToComplete;
    }

    @Override
    public boolean isStopped() {
        if (tasks.size() - numberOfRejected()
                < tasksToComplete)
            return true;
        else
            return false;
    }

    private int numberOfApproved(){
        int approved=0;
        for(Task task:tasks){
            if(task.isApproved())
                approved++;
        }
        return approved;
    }
    
    private int numberOfRejected(){
        int rejected=0;
        for(Task task:tasks){
            if(task.isRejected())
                rejected++;
        }
        return rejected;
    }
    
    @Override
    public Collection<Task> getOpenTasks() {
        Set<Task> runningTasks = new HashSet<Task>();
        if (!isComplete() && !isStopped()) {           
            for (Task task : tasks) {
                if(task.isInProgress() || task.isNotStarted())
                    runningTasks.add(task);
            }    
        }
        return runningTasks;
    }

    public void setTasksToComplete(int tasksToComplete) {
        this.tasksToComplete = tasksToComplete;
    }


    public int getTasksToComplete() {
        return tasksToComplete;
    }

    @Override
    public boolean isComplete() {
        if (numberOfApproved() >= tasksToComplete)
            return true;
        else
            return false;
    }

    
}