/*
 * Copyright 2012 Cisco Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.karaf.support;

import java.util.logging.Logger;

/**
 * Bean to manage externalized configuration using osgi config admin and
 * external properties (.cfg file)
 * 
 * @author Srinivasan Chikkala
 * 
 */
public class BpmnConfigProperties {
    private static final Logger LOG = Logger.getLogger(BpmnConfigProperties.class.getName());
    private boolean localDB;
    private String h2dbTcpPort;
    private String dsDriverClass;
    private String dsUrl;
    private String dsUsername;
    private String dsPassword;

    private String mailServerHost;
    private int mailServerPort;
    private boolean mailServerUseTLS;
    private String mailServerUsername;
    private String mailServerPassword;
    private String mailServerDefaultFrom;    
    
    private String databaseSchemaUpdate;    
    private boolean jobExecutorActivate;
    private String history;

    public boolean isLocalDB() {
        return localDB;
    }

    public void setLocalDB(boolean localDB) {
        this.localDB = localDB;
    }

    public String getH2dbTcpPort() {
        return h2dbTcpPort;
    }

    public void setH2dbTcpPort(String h2dbTcpPort) {
        this.h2dbTcpPort = h2dbTcpPort;
    }

    public String getDsDriverClass() {
        return dsDriverClass;
    }

    public void setDsDriverClass(String dsDriverClass) {
        this.dsDriverClass = dsDriverClass;
    }

    public String getDsUrl() {
        return dsUrl;
    }

    public void setDsUrl(String dsUrl) {
        this.dsUrl = dsUrl;
    }

    public String getDsUsername() {
        return dsUsername;
    }

    public void setDsUsername(String dsUsername) {
        this.dsUsername = dsUsername;
    }

    public String getDsPassword() {
        return dsPassword;
    }

    public void setDsPassword(String dsPassword) {
        this.dsPassword = dsPassword;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }
    
    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public int getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(int mailServerPort) {
        this.mailServerPort = mailServerPort;
    }
    
    
    public boolean isMailServerUseTLS() {
		return mailServerUseTLS;
	}

	public void setMailServerUseTLS(boolean mailServerUseTLS) {
		this.mailServerUseTLS = mailServerUseTLS;
	}

	public String getMailServerUsername() {
		return mailServerUsername;
	}

	public void setMailServerUsername(String mailServerUsername) {
		this.mailServerUsername = mailServerUsername;
	}

	public String getMailServerPassword() {
		return mailServerPassword;
	}

	public void setMailServerPassword(String mailServerPassword) {
		this.mailServerPassword = mailServerPassword;
	}

	public String getMailServerDefaultFrom() {
		return mailServerDefaultFrom;
	}

	public void setMailServerDefaultFrom(String mailServerDefaultFrom) {
		this.mailServerDefaultFrom = mailServerDefaultFrom;
	}

	public String getDatabaseSchemaUpdate() {
        return databaseSchemaUpdate;
    }

    public void setDatabaseSchemaUpdate(String databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }

    public boolean isJobExecutorActivate() {
        return jobExecutorActivate;
    }

    public void setJobExecutorActivate(boolean jobExecutorActivate) {
        this.jobExecutorActivate = jobExecutorActivate;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void print() {
        LOG.info("\nBpmnConfig "
                + "\n[localDB=" + localDB + ",\n h2dbTcpPort=" + h2dbTcpPort
                + ",\n dsDriverClass=" + dsDriverClass + ",\n dsUrl=" + dsUrl
                + ",\n dsUsername=" + dsUsername + ",\n dsPassword=" + "****"
                + ",\n databaseSchemaUpdate=" + databaseSchemaUpdate
                + ",\n mailServerHost=" + mailServerHost
                + ",\n mailServerPort=" + mailServerPort
                + ",\n jobExecutorActivate=" + jobExecutorActivate
                + ",\n history=" + history + "]\n");
    }

}
