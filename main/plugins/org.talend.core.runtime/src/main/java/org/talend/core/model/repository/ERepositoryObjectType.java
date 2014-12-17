// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.talend.core.AbstractDQModelService;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.PluginChecker;
import org.talend.core.model.general.Project;
import org.talend.core.model.properties.BRMSConnectionItem;
import org.talend.core.model.properties.BusinessProcessItem;
import org.talend.core.model.properties.CSVFileConnectionItem;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.DelimitedFileConnectionItem;
import org.talend.core.model.properties.DocumentationItem;
import org.talend.core.model.properties.EDIFACTConnectionItem;
import org.talend.core.model.properties.EbcdicConnectionItem;
import org.talend.core.model.properties.ExcelFileConnectionItem;
import org.talend.core.model.properties.FTPConnectionItem;
import org.talend.core.model.properties.FolderItem;
import org.talend.core.model.properties.GenericSchemaConnectionItem;
import org.talend.core.model.properties.HL7ConnectionItem;
import org.talend.core.model.properties.HeaderFooterConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.JobDocumentationItem;
import org.talend.core.model.properties.JobScriptItem;
import org.talend.core.model.properties.JobletDocumentationItem;
import org.talend.core.model.properties.JobletProcessItem;
import org.talend.core.model.properties.LDAPSchemaConnectionItem;
import org.talend.core.model.properties.LdifFileConnectionItem;
import org.talend.core.model.properties.LinkDocumentationItem;
import org.talend.core.model.properties.LinkRulesItem;
import org.talend.core.model.properties.MDMConnectionItem;
import org.talend.core.model.properties.PigudfItem;
import org.talend.core.model.properties.PositionalFileConnectionItem;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.RegExFileConnectionItem;
import org.talend.core.model.properties.RoutineItem;
import org.talend.core.model.properties.RulesItem;
import org.talend.core.model.properties.SAPConnectionItem;
import org.talend.core.model.properties.SQLPatternItem;
import org.talend.core.model.properties.SVGBusinessProcessItem;
import org.talend.core.model.properties.SalesforceSchemaConnectionItem;
import org.talend.core.model.properties.SnippetItem;
import org.talend.core.model.properties.SnippetVariable;
import org.talend.core.model.properties.TDQItem;
import org.talend.core.model.properties.ValidationRulesConnectionItem;
import org.talend.core.model.properties.WSDLSchemaConnectionItem;
import org.talend.core.model.properties.XmlFileConnectionItem;
import org.talend.core.model.properties.util.PropertiesSwitch;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.core.runtime.i18n.Messages;
import org.talend.core.utils.ProductUtils;
import org.talend.designer.core.ICamelDesignerCoreService;
import org.talend.repository.ProjectManager;

/**
 * DOC hywang class global comment. Detailled comment
 */
public class ERepositoryObjectType extends DynaEnum<ERepositoryObjectType> {

    public static final String PROD_DI = ProductUtils.PROD_DI;

    public static final String PROD_DQ = ProductUtils.PROD_DQ;

    public static final String PROD_CAMEL = ProductUtils.PROD_CAMEL;

    public static final String PROD_MDM = ProductUtils.PROD_MDM;

    public final static ERepositoryObjectType SVN_ROOT = new ERepositoryObjectType("repository.svnroot", "", "SVN_ROOT", true, 1,
            new String[] { PROD_DI, PROD_CAMEL }, new String[] {}, false);

    public final static ERepositoryObjectType SVG_BUSINESS_PROCESS = new ERepositoryObjectType("repository.svgBusinessProcess",
            "businessProcessSVG", "SVG_BUSINESS_PROCESS", true, 3, new String[] { PROD_DI }, new String[] {});

    public final static ERepositoryObjectType SNIPPETS = new ERepositoryObjectType("repository.snippets", "code/snippets",
            "SNIPPETS", true, 10, new String[] { PROD_DI }, new String[] {}, false);

    // public final static ERepositoryObjectType DOCUMENTATION = new ERepositoryObjectType("repository.documentation",
    // "documentations", "DOCUMENTATION", true, 11, new String[] { PROD_DI }, new String[] {});

    public final static ERepositoryObjectType METADATA = new ERepositoryObjectType("repository.metadata", "metadata", "METADATA",
            true, 12, new String[] { PROD_DI, PROD_DQ }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_CON_TABLE = new ERepositoryObjectType("repository.metadataTable",
            "METADATA_CON_TABLE", 13, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_CON_COLUMN = new ERepositoryObjectType("repository.metadataColumn",
            "METADATA_CON_COLUMN", 14, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_CON_VIEW = new ERepositoryObjectType("repository.metadataView",
            "METADATA_CON_VIEW", 15, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_CON_CATALOG = new ERepositoryObjectType("repository.metadataCatalog",
            "METADATA_CON_CATALOG", 16, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_CON_SCHEMA = new ERepositoryObjectType("repository.metadataSchema",
            "METADATA_CON_SCHEMA", 17, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_CON_SYNONYM = new ERepositoryObjectType("repository.synonym",
            "METADATA_CON_SYNONYM", 18, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_CON_QUERY = new ERepositoryObjectType("repository.query",
            "METADATA_CON_QUERY", 19, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_CON_CDC = new ERepositoryObjectType("repository.CDC", "METADATA_CON_CDC",
            20, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_SAP_FUNCTION = new ERepositoryObjectType(
            "repository.SAPFunction", "METADATA_SAP_FUNCTION", 21, true, true, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_SAP_IDOC = new ERepositoryObjectType("repository.SAPIDoc",
            "METADATA_SAP_IDOC", 22, true, true, new String[] { PROD_DI }, new String[] {}, false);

    public final static ERepositoryObjectType MDM_CONCEPT = new ERepositoryObjectType(
            "repository.concept", "MDM_CONCEPT", 23, true, true, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    public final static ERepositoryObjectType MDM_SCHEMA = new ERepositoryObjectType(
            "repository.xmlSchema", "MDM_SCHEMA", 24, true, true, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    public final static ERepositoryObjectType MDM_ELEMENT_TYPE = new ERepositoryObjectType(
            "repository.xmlElementType", "MDM_ELEMENT_TYPE", 25, true, true, new String[] { PROD_DI }, new String[] {}, false);//$NON-NLS-1$

    public final static ERepositoryObjectType RECYCLE_BIN = new ERepositoryObjectType("repository.recyclebin", "", "RECYCLE_BIN",
            true, 26, new String[] { PROD_DI, PROD_CAMEL }, new String[] {}, false);

    public final static ERepositoryObjectType METADATA_COLUMN = new ERepositoryObjectType("repository.column", "",
            "METADATA_COLUMN", true, 27, new String[] { PROD_DI }, new String[] {}, false);

    // feature 0006484 add
    public final static ERepositoryObjectType METADATA_FILE_RULES = new ERepositoryObjectType(
            "repository.metadataFileRules", "metadata/rules", "METADATA_FILE_RULES", 28, true, "repository.metadataFileRules.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType METADATA_FILE_LINKRULES = new ERepositoryObjectType(
            "repository.metadataLinkFileRules", "metadata/rules", "METADATA_FILE_LINKRULES", 29, true, "repository.metadataLinkFileRules.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType METADATA_RULES_MANAGEMENT = new ERepositoryObjectType(
            "repository.metadataRulesManagement", "", "METADATA_RULES_MANAGEMENT", 30, true, "repository.metadataRulesManagement.alias", new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    // public final static ERepositoryObjectType METADATA_CONNECTIONS = new ERepositoryObjectType(
    //            "repository.metadataConnections", "metadata/connections", "METADATA_CONNECTIONS", 31, true, "repository.metadataConnections.alias", new String[] { PROD_DI, PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // public final static ERepositoryObjectType METADATA_SAPCONNECTIONS = new ERepositoryObjectType(
    //            "repository.metadataSAPConnections", "metadata/sapconnections", "METADATA_SAPCONNECTIONS", 32, true, "repository.metadataSAPConnections.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // public final static ERepositoryObjectType METADATA_EDIFACT = new
    // ERepositoryObjectType("repositorymetadataEDIFact",
    // "metadata/EDISchema", "METADATA_EDIFACT", 50, true, "repositorymetadataEDIFact.alias", new String[] { PROD_DI },
    // new String[] {});

    // public final static ERepositoryObjectType SQLPATTERNS = new ERepositoryObjectType(
    //            "repository.metadataSQLPatterns", "sqlPatterns", "SQLPATTERNS", 33, true, "repository.metadataSQLPatterns.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // public final static ERepositoryObjectType METADATA_FILE_EBCDIC = new ERepositoryObjectType(
    //            "repository.metadataFileEDCDIC", "metadata/fileEBCDIC", "METADATA_FILE_EBCDIC", 34, true, "repository.metadataFileEDCDIC.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // public final static ERepositoryObjectType METADATA_FILE_HL7 = new ERepositoryObjectType(
    //            "repository.metadataFileHL7", "metadata/fileHL7", "METADATA_FILE_HL7", 35, true, "repository.metadataFileHL7.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // public final static ERepositoryObjectType METADATA_FILE_FTP = new ERepositoryObjectType(
    //            "repository.metadataFileFTP", "metadata/FTPconnections", "METADATA_FILE_FTP", 36, true, "repository.metadataFileFTP.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // 0015169 added
    public final static ERepositoryObjectType METADATA_FILE_BRMS = new ERepositoryObjectType(
            "repository.metadataFileBRMS", "metadata/BRMSconnections", "METADATA_FILE_BRMS", 37, true, "repository.metadataFileBRMS.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // public final static ERepositoryObjectType METADATA_FILE_DELIMITED = new ERepositoryObjectType(
    //            "repository.metadataFileDelimited", "metadata/fileDelimited", "METADATA_FILE_DELIMITED", 39, true, "repository.metadataFileDelimited.alias", new String[] { PROD_DI, PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // public final static ERepositoryObjectType METADATA_VALIDATION_RULES = new ERepositoryObjectType(
    //            "repository.metadataValidationRules", "metadata/validationRules", "METADATA_VALIDATION_RULES", 48, true, "repository.metadataValidationRules.alias", new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType METADATA_VALIDATIONS_RULES_FOLDER = new ERepositoryObjectType(
            "repository.metadataValidationRulesFolder", "", "METADATA_VALIDATIONS_RULES_FOLDER", 49, true, "repository.metadataValidationRulesFolder.alias", new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType FOLDER = new ERepositoryObjectType(
            "repository.folder", "", "FOLDER", true, 50, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    public final static ERepositoryObjectType REFERENCED_PROJECTS = new ERepositoryObjectType(
            "repository.referencedProjects", "", "REFERENCED_PROJECTS", 51, true, "repository.referencedProjects.alias", new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType GENERATED = new ERepositoryObjectType(
            "repository.generated", "documentations/generated", "GENERATED", true, 52, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    public final static ERepositoryObjectType JOBS = new ERepositoryObjectType(
            "repository.jobs", "documentations/generated/jobs", "JOBS", true, 53, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    public final static ERepositoryObjectType JOB_DOC = new ERepositoryObjectType(
            "repository.jobdoc", "documentations/generated/jobs", "JOB_DOC", true, 54, new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$

    public final static ERepositoryObjectType LIBS = new ERepositoryObjectType(
            "repository.libs", "libs", "LIBS", true, 56, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    public final static ERepositoryObjectType JOBLETS = new ERepositoryObjectType(
            "repository.joblets", "documentations/generated/joblets", "JOBLETS", true, 58, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    public final static ERepositoryObjectType JOBLET_DOC = new ERepositoryObjectType(
            "repository.jobletdoc", "documentations/generated/joblets", "JOBLET_DOC", true, 59, new String[] { PROD_DI }, new String[] {}); //$NON-NLS-1$

    public final static ERepositoryObjectType COMPONENTS = new ERepositoryObjectType(
            "repository.components", "components", "COMPONENTS", true, 61, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    // MOD mzhao feature 9207
    public final static ERepositoryObjectType TDQ_ELEMENT = new ERepositoryObjectType(
            "repository.tdqelement", "", "TDQ_ELEMENT", 62, true, "repository.tdqelement.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    // MOD mzhao feature 13114, 2010-05-19
    public final static ERepositoryObjectType TDQ_ANALYSIS_ELEMENT = new ERepositoryObjectType(
            "repository.tdqelement.analysis", "TDQ_Data Profiling/Analyses", "TDQ_ANALYSIS_ELEMENT", 63, true, "repository.tdqelement.analysis.alias", new String[] { PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_REPORT_ELEMENT = new ERepositoryObjectType(
            "repository.tdqelement.report", "TDQ_Data Profiling/Reports", "TDQ_REPORT_ELEMENT", 64, true, "repository.tdqelement.report.alias", new String[] { PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_INDICATOR_ELEMENT = new ERepositoryObjectType(
            "repository.tdqelement.indicator", "TDQ_Libraries/Indicators", "TDQ_INDICATOR_ELEMENT", 66, true, "repository.tdqelement.indicator.alias", new String[] { PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_PATTERN_ELEMENT = new ERepositoryObjectType(
            "repository.tdqelement.pattern", "TDQ_Libraries/Patterns", "TDQ_PATTERN_ELEMENT", 67, true, "repository.tdqelement.pattern.alias", new String[] { PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_SOURCE_FILE_ELEMENT = new ERepositoryObjectType(
            "repository.tdqelement.sourceFile", "TDQ_Libraries/Source Files", "TDQ_SOURCE_FILE_ELEMENT", 68, true, "repository.tdqelement.sourceFile.alias", new String[] { PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    // MOD zqin feature 14507
    public final static ERepositoryObjectType TDQ_JRAXML_ELEMENT = new ERepositoryObjectType(
            "repository.tdqelement.jrxml", "TDQ_Libraries/JRXML Template", "TDQ_JRAXML_ELEMENT", 69, true, "repository.tdqelement.jrxml.alias", new String[] { PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_FOLDER_NODE = new ERepositoryObjectType(
            "repository.tdqelement.folderNode", "", "TDQ_FOLDER_NODE", 70, true, "repository.tdqelement.folderNode.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    // MOD klliu feature 15750
    public final static ERepositoryObjectType TDQ_DATA_PROFILING = new ERepositoryObjectType(
            "repository.dataprofiling", "TDQ_Data Profiling", "TDQ_DATA_PROFILING", 71, true, "repository.dataprofiling.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_LIBRARIES = new ERepositoryObjectType(
            "repository.libraries", "TDQ_Libraries", "TDQ_LIBRARIES", 74, true, "repository.libraries.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_PATTERN_REGEX = new ERepositoryObjectType(
            "repository.patternRegex", "TDQ_Libraries/Patterns/Regex", "TDQ_PATTERN_REGEX", 76, true, "repository.patternRegex.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_PATTERN_SQL = new ERepositoryObjectType(
            "repository.patternSql", "TDQ_Libraries/Patterns/SQL", "TDQ_PATTERN_SQL", 77, true, "repository.patternSql.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_RULES = new ERepositoryObjectType(
            "repository.rules", "TDQ_Libraries/Rules", "TDQ_RULES", 79, true, "repository.rules.alias", new String[] { PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_RULES_SQL = new ERepositoryObjectType(
            "repository.rulesSql", "TDQ_Libraries/Rules/SQL", "TDQ_RULES_SQL", 80, true, "repository.rulesSql.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    // MOD klliu feature 23109

    public final static ERepositoryObjectType TDQ_RULES_PARSER = new ERepositoryObjectType(
            "repository.rulesParser", "TDQ_Libraries/Rules/Parser", "TDQ_RULES_PARSER", 81, true, "repository.rulesParser.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_RULES_MATCHER = new ERepositoryObjectType(
            "repository.rulesMatcher", "TDQ_Libraries/Rules/Match", "TDQ_RULES_MATCHER", 82, true, "repository.rulesMatcher.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    // ~
    // MOD klliu 2010-11-26 definition type

    public final static ERepositoryObjectType TDQ_SYSTEM_INDICATORS = new ERepositoryObjectType(
            "repository.systemIndicators", "TDQ_Libraries/Indicators/System Indicators", "TDQ_SYSTEM_INDICATORS", 83, true, "repository.systemIndicators.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_USERDEFINE_INDICATORS = new ERepositoryObjectType(
            "repository.userDefineIndicators", "TDQ_Libraries/Indicators/User Defined Indicators", "TDQ_USERDEFINE_INDICATORS", 84, true, "repository.userDefineIndicators.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_USERDEFINE_INDICATORS_LIB = new ERepositoryObjectType(
            "repository.userDefineIndicators.lib", "", "TDQ_USERDEFINE_INDICATORS_LIB", 85, true, "repository.userDefineIndicators.lib.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_ADVANCED_STATISTICS = new ERepositoryObjectType(
            "repository.systemIndicators.advancedStatistics", "TDQ_Libraries/Indicators/System Indicators/Advanced Statistics", "SYSTEM_INDICATORS_ADVANCED_STATISTICS", 86, true, "repository.systemIndicators.advancedStatistics.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_BUSINESS_RULES = new ERepositoryObjectType(
            "repository.systemIndicators.businessRules", "TDQ_Libraries/Indicators/System Indicators/Business Rules", "SYSTEM_INDICATORS_BUSINESS_RULES", 87, true, "repository.systemIndicators.businessRules.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_CORRELATION = new ERepositoryObjectType(
            "repository.systemIndicators.correlation", "TDQ_Libraries/Indicators/System Indicators/Correlation", "SYSTEM_INDICATORS_CORRELATION", 88, true, "repository.systemIndicators.correlation.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_FUNCTIONAL_DEPENDENCY = new ERepositoryObjectType(
            "repository.systemIndicators.functionalDependency", "TDQ_Libraries/Indicators/System Indicators/Functional Dependency", "SYSTEM_INDICATORS_FUNCTIONAL_DEPENDENCY", 89, true, "repository.systemIndicators.functionalDependency.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_OVERVIEW = new ERepositoryObjectType(
            "repository.systemIndicators.overview", "TDQ_Libraries/Indicators/System Indicators/Overview", "SYSTEM_INDICATORS_OVERVIEW", 90, true, "repository.systemIndicators.overview.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_PATTERN_FINDER = new ERepositoryObjectType(
            "repository.systemIndicators.patternFinder", "TDQ_Libraries/Indicators/System Indicators/Pattern Finder", "SYSTEM_INDICATORS_PATTERN_FINDER", 91, true, "repository.systemIndicators.patternFinder.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_PATTERN_MATCHING = new ERepositoryObjectType(
            "repository.systemIndicators.patternMatching", "TDQ_Libraries/Indicators/System Indicators/Pattern Matching", "SYSTEM_INDICATORS_PATTERN_MATCHING", 92, true, "repository.systemIndicators.patternMatching.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_ROW_COMPARISON = new ERepositoryObjectType(
            "repository.systemIndicators.rowComparison", "TDQ_Libraries/Indicators/System Indicators/Row Comparison", "SYSTEM_INDICATORS_ROW_COMPARISON", 93, true, "repository.systemIndicators.rowComparison.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_SIMPLE_STATISTICS = new ERepositoryObjectType(
            "repository.systemIndicators.simpleStatistics", "TDQ_Libraries/Indicators/System Indicators/Simple Statistics", "SYSTEM_INDICATORS_SIMPLE_STATISTICS", 94, true, "repository.systemIndicators.simpleStatistics.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_SOUNDEX = new ERepositoryObjectType(
            "repository.systemIndicators.soundex", "TDQ_Libraries/Indicators/System Indicators/Soundex", "SYSTEM_INDICATORS_SOUNDEX", 95, true, "repository.systemIndicators.soundex.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_SUMMARY_STATISTICS = new ERepositoryObjectType(
            "repository.systemIndicators.summaryStatistics", "TDQ_Libraries/Indicators/System Indicators/Summary Statistics", "SYSTEM_INDICATORS_SUMMARY_STATISTICS", 96, true, "repository.systemIndicators.summaryStatistics.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_TEXT_STATISTICS = new ERepositoryObjectType(
            "repository.systemIndicators.textStatistics", "TDQ_Libraries/Indicators/System Indicators/Text Statistics", "SYSTEM_INDICATORS_TEXT_STATISTICS", 97, true, "repository.systemIndicators.textStatistics.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType TDQ_EXCHANGE = new ERepositoryObjectType(
            "repository.tdqExchange", "TDQ_Libraries/Exchange", "TDQ_EXCHANGE", 98, true, "repository.tdqExchange.alias", new String[] { PROD_DQ }, new String[] {}); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType METADATA_SALESFORCE_MODULE = new ERepositoryObjectType(
            "repository.metadataSalesforceModule", "METADATA_SALESFORCE_MODULE", 99, true, true, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_PHONENUMBER_STATISTICS = new ERepositoryObjectType(
            "repository.systemIndicators.phoneNumberStatistics", "TDQ_Libraries/Indicators/System Indicators/Phone Number Statistics", "SYSTEM_INDICATORS_PHONENUMBER_STATISTICS", 100, true, "repository.systemIndicators.phoneNumberStatistics.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SERVICESOPERATION = new ERepositoryObjectType(
            "repository.servicesOperation", "SERVICESOPERATION", 101, true, true, new String[] { PROD_DI, PROD_CAMEL }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SERVICESPORT = new ERepositoryObjectType(
            "repository.servicesPort", "SERVICESPORT", 102, true, true, new String[] { PROD_DI, PROD_CAMEL }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType SYSTEM_INDICATORS_FRAUDDETECTION = new ERepositoryObjectType(
            "repository.systemIndicators.fraudDetection", "TDQ_Libraries/Indicators/System Indicators/Fraud Detection", "SYSTEM_INDICATORS_FRAUDDETECTION", 103, true, "repository.systemIndicators.fraudDetectionStatistics.alias", new String[] { PROD_DQ }, new String[] {}, false); //$NON-NLS-1$ //$NON-NLS-2$

    public final static ERepositoryObjectType METADATA_SAP_TABLE = new ERepositoryObjectType(
            "repository.SAPTable", "METADATA_SAP_TABLE", 104, true, true, new String[] { PROD_DI }, new String[] {}, false); //$NON-NLS-1$

    private String label;

    private String alias;

    private String folder = "";

    private String[] products;

    private boolean subItem;

    private boolean isResouce = true;

    private String[] userRight;

    private String namePattern = null;

    /*
     * this used to indicate the multiple same name items are allowed to created or not, In generic, those resources or
     * items should be in different folder.
     */
    private boolean isAllowMultiName = false;

    /*
     * This attribute is used to indicate folder name using ResourcesPlugin.getWorkspace().validateName(label,
     * IResource.FOLDER) to validate but not talend default folder pattern when the value is true.
     */
    private boolean isAllowPlainFolder = false;

    private List<ERepositoryObjectType> parentTypes = new ArrayList<ERepositoryObjectType>();

    private List<ERepositoryObjectType> childrenTypes = new ArrayList<ERepositoryObjectType>();
    static {
        // init
        RepositoryNodeProviderRegistryReader.getInstance().init();
    }

    public final static ERepositoryObjectType BUSINESS_PROCESS = ERepositoryObjectType.valueOf("BUSINESS_PROCESS"); //$NON-NLS-1$

    public final static ERepositoryObjectType PROCESS = ERepositoryObjectType.valueOf("PROCESS"); //$NON-NLS-1$

    public final static ERepositoryObjectType JOBLET = ERepositoryObjectType.valueOf("JOBLET"); //$NON-NLS-1$

    public final static ERepositoryObjectType CODE = ERepositoryObjectType.valueOf("CODE"); //$NON-NLS-1$

    public final static ERepositoryObjectType ROUTINES = ERepositoryObjectType.valueOf("ROUTINES"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_HEADER_FOOTER = ERepositoryObjectType.valueOf("METADATA_HEADER_FOOTER"); //$NON-NLS-1$

    public final static ERepositoryObjectType JOB_SCRIPT = ERepositoryObjectType.valueOf("JOB_SCRIPT"); //$NON-NLS-1$

    public final static ERepositoryObjectType PIG_UDF = ERepositoryObjectType.valueOf("PIG_UDF"); //$NON-NLS-1$

    public final static ERepositoryObjectType CONTEXT = ERepositoryObjectType.valueOf("CONTEXT"); //$NON-NLS-1$

    public final static ERepositoryObjectType SQLPATTERNS = ERepositoryObjectType.valueOf("SQLPATTERNS"); //$NON-NLS-1$ 

    public final static ERepositoryObjectType DOCUMENTATION = ERepositoryObjectType.valueOf("DOCUMENTATION"); //$NON-NLS-1$ 

    public final static ERepositoryObjectType METADATA_FILE_POSITIONAL = ERepositoryObjectType
            .valueOf("METADATA_FILE_POSITIONAL"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_FILE_REGEXP = ERepositoryObjectType.valueOf("METADATA_FILE_REGEXP"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_FILE_XML = ERepositoryObjectType.valueOf("METADATA_FILE_XML"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_FILE_LDIF = ERepositoryObjectType.valueOf("METADATA_FILE_LDIF"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_FILE_EXCEL = ERepositoryObjectType.valueOf("METADATA_FILE_EXCEL"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_SALESFORCE_SCHEMA = ERepositoryObjectType
            .valueOf("METADATA_SALESFORCE_SCHEMA"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_LDAP_SCHEMA = ERepositoryObjectType.valueOf("METADATA_LDAP_SCHEMA"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_WSDL_SCHEMA = ERepositoryObjectType.valueOf("METADATA_WSDL_SCHEMA"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_GENERIC_SCHEMA = ERepositoryObjectType.valueOf("METADATA_GENERIC_SCHEMA"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_MDMCONNECTION = ERepositoryObjectType.valueOf("METADATA_MDMCONNECTION"); //$NON-NLS-1$

    public final static ERepositoryObjectType PROCESS_MR = ERepositoryObjectType.valueOf("PROCESS_MR"); //$NON-NLS-1$

    public final static ERepositoryObjectType METADATA_CONNECTIONS = ERepositoryObjectType.valueOf("METADATA_CONNECTIONS");

    public final static ERepositoryObjectType METADATA_FILE_DELIMITED = ERepositoryObjectType.valueOf("METADATA_FILE_DELIMITED");

    public final static ERepositoryObjectType METADATA_FILE_FTP = ERepositoryObjectType.valueOf("METADATA_FILE_FTP");

    public final static ERepositoryObjectType METADATA_SAPCONNECTIONS = ERepositoryObjectType.valueOf("METADATA_SAPCONNECTIONS");

    public final static ERepositoryObjectType METADATA_FILE_EBCDIC = ERepositoryObjectType.valueOf("METADATA_FILE_EBCDIC");

    public final static ERepositoryObjectType METADATA_VALIDATION_RULES = ERepositoryObjectType
            .valueOf("METADATA_VALIDATION_RULES");

    public final static ERepositoryObjectType METADATA_FILE_HL7 = ERepositoryObjectType.valueOf("METADATA_FILE_HL7");

    public final static ERepositoryObjectType METADATA_EDIFACT = ERepositoryObjectType.valueOf("METADATA_EDIFACT");

    public final static ERepositoryObjectType PROCESS_STORM = ERepositoryObjectType.valueOf("PROCESS_STORM");

    public final static ERepositoryObjectType HDFS = ERepositoryObjectType.valueOf("HDFS");

    ERepositoryObjectType(String key, String folder, String type, boolean isStaticNode, int ordinal, String[] products,
            String[] userRight, boolean... isResouce) {
        super(key, type, isStaticNode, ordinal);
        this.folder = folder;
        this.products = products;
        this.userRight = userRight;
        if (isResouce != null && isResouce.length == 1) {
            this.isResouce = isResouce[0];
        }
    }

    ERepositoryObjectType(String key, String folder, String type, boolean isStaticNode, int ordinal, String[] products,
            boolean isAllowMultiName, String[] userRight, boolean... isResouce) {
        this(key, folder, type, isStaticNode, ordinal, products, userRight, isResouce);
        this.isAllowMultiName = isAllowMultiName;
    }

    ERepositoryObjectType(String key, String folder, String type, boolean isStaticNode, int ordinal, String[] products,
            String[] userRight, boolean isAllowMultiName, String namePattern, boolean... isResouce) {
        this(key, folder, type, isStaticNode, ordinal, products, isAllowMultiName, userRight, isResouce);
        this.namePattern = namePattern;
    }

    ERepositoryObjectType(String key, String type, int ordinal, boolean isStaticNode, boolean subItem, String[] products,
            String[] userRight, boolean... isResouce) {
        super(key, type, isStaticNode, ordinal);
        this.subItem = subItem;
        this.products = products;
        this.userRight = userRight;
        if (isResouce != null && isResouce.length == 1) {
            this.isResouce = isResouce[0];
        }
    }

    ERepositoryObjectType(String key, String type, int ordinal, boolean isStaticNode, boolean subItem, String[] products,
            boolean isAllowMultiName, String[] userRight, boolean... isResouce) {
        this(key, type, ordinal, isStaticNode, subItem, products, userRight, isResouce);
        this.isAllowMultiName = isAllowMultiName;
    }

    ERepositoryObjectType(String key, String type, int ordinal, boolean isStaticNode, boolean subItem, String[] products,
            String[] userRight, boolean isAllowMultiName, String namePattern, boolean... isResouce) {
        this(key, type, ordinal, isStaticNode, subItem, products, isAllowMultiName, userRight, isResouce);
        this.namePattern = namePattern;
    }

    ERepositoryObjectType(String key, String folder, String type, int ordinal, boolean isStaticNode, String alias,
            String[] products, String[] userRight, boolean... isResouce) {
        this(key, folder, type, isStaticNode, ordinal, products, userRight, isResouce);
        this.alias = alias;
    }

    ERepositoryObjectType(String key, String folder, String type, int ordinal, boolean isStaticNode, String alias,
            String[] products, boolean isAllowMultiName, String[] userRight, boolean... isResouce) {
        this(key, folder, type, ordinal, isStaticNode, alias, products, userRight, isResouce);
        this.isAllowMultiName = isAllowMultiName;
    }

    ERepositoryObjectType(String key, String folder, String type, int ordinal, boolean isStaticNode, String alias,
            String[] products, String[] userRight, boolean isAllowMultiName, String namePattern, boolean... isResouce) {
        this(key, folder, type, ordinal, isStaticNode, alias, products, isAllowMultiName, userRight, isResouce);
        this.namePattern = namePattern;
    }

    ERepositoryObjectType(String key, String label, String folder, String type, int ordinal, boolean isStaticNode, String alias,
            String[] products, String[] userRight, boolean... isResouce) {
        this(key, folder, type, ordinal, isStaticNode, alias, products, userRight, isResouce);
        this.label = label;
    }

    ERepositoryObjectType(String key, String label, String folder, String type, int ordinal, boolean isStaticNode, String alias,
            String[] products, boolean isAllowMultiName, String[] userRight, boolean... isResouce) {
        this(key, label, folder, type, ordinal, isStaticNode, alias, products, userRight, isResouce);
        this.isAllowMultiName = isAllowMultiName;
    }

    ERepositoryObjectType(String key, String label, String folder, String type, int ordinal, boolean isStaticNode, String alias,
            String[] products, String[] userRight, boolean isAllowMultiName, String namePattern, boolean... isResouce) {
        this(key, folder, type, ordinal, isStaticNode, alias, products, isAllowMultiName, userRight, isResouce);
        this.namePattern = namePattern;
        this.label = label;
    }

    public static <E> DynaEnum<? extends DynaEnum<?>>[] values() {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        String projectType = null;
        if (currentProject != null) {
            projectType = currentProject.getEmfProject().getType();
        }
        if (PROD_DI.equals(projectType)) {
            // limit with DI items only.
            List<ERepositoryObjectType> toReturn = new ArrayList<ERepositoryObjectType>();
            for (ERepositoryObjectType currentType : values(ERepositoryObjectType.class)) {
                if (ArrayUtils.contains(currentType.getProducts(), PROD_DI)
                        || ArrayUtils.contains(currentType.getProducts(), PROD_CAMEL)) {
                    toReturn.add(currentType);
                }
            }
            return toReturn.toArray(new ERepositoryObjectType[] {});

        }
        // Bad code here to fix bug TDI-23178. If DQ repository type implemented by extention point like what MDM does
        // will avoid this bug.
        if (!PluginChecker.isPluginLoaded("org.talend.dataprofiler.core")) { //$NON-NLS-1$
            List<ERepositoryObjectType> toReturn = new ArrayList<ERepositoryObjectType>();
            for (ERepositoryObjectType currentType : values(ERepositoryObjectType.class)) {
                if (!(currentType.getProducts().length == 1 && ArrayUtils.contains(currentType.getProducts(), PROD_DQ))) {
                    toReturn.add(currentType);
                }
            }
            return toReturn.toArray(new ERepositoryObjectType[0]);
        }

        return values(ERepositoryObjectType.class);
    }

    @SuppressWarnings("unchecked")
    private static <E> Constructor<E> getConstructor(Class<E> clazz, Class<?>[] argTypes) {
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                return (Constructor<E>) c.getDeclaredConstructor(argTypes);
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    public String getLabel() {
        if (this.label == null) {
            if (isStaticNode()) {
                return super.toString();
            } else {
                return getType();
            }
        }
        return this.label;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.repository.DynaEnum#toString()
     */
    @Override
    public String toString() {
        return getLabel();
    }

    public String getAlias() {
        if (alias == null) {
            return null;
        }
        if (isStaticNode()) {
            return Messages.getString(alias);
        }
        return alias;
    }

    public static ERepositoryObjectType getTypeFromKey(String key) {
        for (ERepositoryObjectType type : (ERepositoryObjectType[]) values()) {
            if (type.getKey().equals(key)) {
                return type;
            }
        }
        return null;
    }

    public static ERepositoryObjectType getType(String typeName) {
        for (ERepositoryObjectType type : (ERepositoryObjectType[]) values()) {
            if (type.getType().equals(typeName)) {
                return type;
            }
        }
        return null;
    }

    public boolean hasFolder() {
        try {
            String folderName = getFolderName(this);
            if (folderName != null && !"".equals(folderName)) { //$NON-NLS-1$
                return true;
            }
        } catch (IllegalArgumentException e) { // not found
            // nothing to do
        }
        return false;
    }

    public static String getFolderName(ERepositoryObjectType type) {

        if (type == GENERATED || type == JOBS || type == JOB_DOC) {
            if ((PluginChecker.isDocumentationPluginLoaded())) {
                return type.getFolder();
            }
        }
        if (type == JOBLETS || type == JOBLET_DOC) {
            if (PluginChecker.isJobLetPluginLoaded()) {
                return type.getFolder();
            }
        } else {

            return type.getFolder();
        }
        throw new IllegalArgumentException(Messages.getString("ERepositoryObjectType.FolderNotFound", type)); //$NON-NLS-1$
    }

    public static String getDeleteFolderName(ERepositoryObjectType type) {
        if (type == BUSINESS_PROCESS) {
            return "businessProcess"; //$NON-NLS-1$
        } else if (type == SVG_BUSINESS_PROCESS) {
            return "businessProcessSVG"; //$NON-NLS-1$
        } else if (type == PROCESS) {
            return "job"; //$NON-NLS-1$
        } else if (type == JOBLET) {
            return "joblet";
        } else if (type == CONTEXT) {
            return "context"; //$NON-NLS-1$
        } else if (type == ROUTINES) {
            return "routine"; //$NON-NLS-1$
        } else if (type == JOB_SCRIPT) {
            return "jobscript"; //$NON-NLS-1$
        } else if (type == SNIPPETS) {
            return "snippet"; //$NON-NLS-1$
        } else if (type == DOCUMENTATION) {
            return "documentation"; //$NON-NLS-1$
        } else if (type == SQLPATTERNS) {
            return "sqlPattern"; //$NON-NLS-1$
        } else if (type == METADATA) {
            return "metadata"; //$NON-NLS-1$
        } else if (type == METADATA_CONNECTIONS) {
            return "DB connection"; //$NON-NLS-1$
        } else if (type == METADATA_SAPCONNECTIONS) {
            return "SAPconnection"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_EBCDIC) {
            return "fileEBCDIC"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_HL7) {
            return "fileHL7"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_FTP) {
            return "FTPconnection"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_BRMS) {
            return "BRMSconnection"; //$NON-NLS-1$
        } else if (type == METADATA_MDMCONNECTION) {
            return "MDMconnection"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_DELIMITED) {
            return "fileDelimited"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_POSITIONAL) {
            return "filePositional"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_REGEXP) {
            return "fileRegex"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_XML) {
            return "fileXml"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_EXCEL) {
            return "fileExcel"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_LDIF) {
            return "fileLdif"; //$NON-NLS-1$
        } else if (type == METADATA_LDAP_SCHEMA) {
            return "LDAPSchema"; //$NON-NLS-1$
        } else if (type == METADATA_GENERIC_SCHEMA) {
            return "genericSchema"; //$NON-NLS-1$
        } else if (type == METADATA_WSDL_SCHEMA) {
            return "WSDLSchema"; //$NON-NLS-1$
        } else if (type == METADATA_SALESFORCE_SCHEMA) {
            return "SalesforceSchema"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_RULES) {
            return "rules"; //$NON-NLS-1$
        } else if (type == METADATA_FILE_LINKRULES) {
            return "rules"; //$NON-NLS-1$
        } else if (type == METADATA_HEADER_FOOTER) {
            return "header_footer";
        } else if (type == TDQ_ANALYSIS_ELEMENT) {
            return "Analyses"; //$NON-NLS-1$
        } else if (type == TDQ_RULES_SQL) {
            return "Rules"; //$NON-NLS-1$
        } else if (type == TDQ_INDICATOR_ELEMENT) {
            return "Indicators"; //$NON-NLS-1$
        } else if (type == TDQ_PATTERN_ELEMENT) {
            return "Patterns"; //$NON-NLS-1$
        } else if (type == TDQ_JRAXML_ELEMENT) {
            return "JRXML Template";
        } else if (type == TDQ_REPORT_ELEMENT) {
            return "Reports"; //$NON-NLS-1$
        } else if (type == TDQ_ELEMENT) {
            return "";//$NON-NLS-1$
        } else if (type == COMPONENTS) {
            return "components";
        } else if (type.getType().equals("SERVICES")) {
            return "Services";
        } else if (GlobalServiceRegister.getDefault().isServiceRegistered(ICamelDesignerCoreService.class)) {
            ICamelDesignerCoreService camelService = (ICamelDesignerCoreService) GlobalServiceRegister.getDefault().getService(
                    ICamelDesignerCoreService.class);
            String deleteFolderName = camelService.getDeleteFolderName(type);
            if (deleteFolderName != null) {
                return deleteFolderName;
            }
        }

        return "job";
    }

    public static ERepositoryObjectType getItemType(Item item) {

        ERepositoryObjectType repObjType = getTDQRepObjType(item);
        if (repObjType != null) {
            return repObjType;
        }
        repObjType = getRepositoryObjectType(item);
        if (repObjType != null) {
            return repObjType;
        }
        return (ERepositoryObjectType) new PropertiesSwitch() {

            @Override
            public Object caseFolderItem(FolderItem object) {
                return FOLDER;
            }

            @Override
            public Object caseDocumentationItem(DocumentationItem object) {
                return DOCUMENTATION;
            }

            @Override
            public Object caseLinkDocumentationItem(LinkDocumentationItem object) {
                return DOCUMENTATION;
            }

            @Override
            public Object caseRulesItem(RulesItem object) {
                return METADATA_FILE_RULES;
            }

            @Override
            public Object caseLinkRulesItem(LinkRulesItem object) {
                return METADATA_FILE_LINKRULES;
            }

            /*
             * (non-Javadoc)
             * 
             * @seeorg.talend.core.model.properties.util.PropertiesSwitch# caseJobDocumentationItem
             * (org.talend.core.model.properties.JobDocumentationItem)
             */
            @Override
            public Object caseJobDocumentationItem(JobDocumentationItem object) {
                return JOB_DOC;
            }

            /*
             * (non-Javadoc)
             * 
             * @seeorg.talend.core.model.properties.util.PropertiesSwitch# caseJobletDocumentationItem
             * (org.talend.core.model.properties.JobletDocumentationItem)
             */
            @Override
            public Object caseJobletDocumentationItem(JobletDocumentationItem object) {
                return JOBLET_DOC;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.talend.core.model.properties.util.PropertiesSwitch#casePigudfItem(org.talend.core.model.properties
             * .PigudfItem)
             */
            @Override
            public Object casePigudfItem(PigudfItem object) {
                return PIG_UDF;
            }

            @Override
            public Object caseRoutineItem(RoutineItem object) {
                return ROUTINES;
            }

            // public Object caseBeanItem(BeanItem object) {
            // return BEANS;
            // }

            @Override
            public Object caseJobScriptItem(JobScriptItem object) {
                return JOB_SCRIPT;
            }

            /*
             * (non-Javadoc)
             * 
             * @seeorg.talend.core.model.properties.util.PropertiesSwitch# caseSQLPatternItem
             * (org.talend.core.model.properties.SQLPatternItem)
             */
            @Override
            public Object caseSQLPatternItem(SQLPatternItem object) {
                return SQLPATTERNS;
            }

            @Override
            public Object caseProcessItem(ProcessItem object) {

                return PROCESS;
            }

            /*
             * (non-Javadoc)
             * 
             * @seeorg.talend.core.model.properties.util.PropertiesSwitch# caseJobletProcessItem
             * (org.talend.core.model.properties.JobletProcessItem)
             */
            @Override
            public Object caseJobletProcessItem(JobletProcessItem object) {
                return JOBLET;
            }

            @Override
            public Object caseContextItem(ContextItem object) {
                return CONTEXT;
            }

            @Override
            public Object caseSnippetItem(SnippetItem object) {
                return SNIPPETS;
            }

            @Override
            public Object caseSnippetVariable(SnippetVariable object) {
                return SNIPPETS;
            }

            @Override
            public Object caseBusinessProcessItem(BusinessProcessItem object) {
                return BUSINESS_PROCESS;
            }

            @Override
            public Object caseCSVFileConnectionItem(CSVFileConnectionItem object) {
                throw new IllegalStateException(Messages.getString("ERepositoryObjectType.NotImplemented")); //$NON-NLS-1$
            }

            @Override
            public Object caseDatabaseConnectionItem(DatabaseConnectionItem object) {
                return METADATA_CONNECTIONS;
            }

            @Override
            public Object caseSAPConnectionItem(SAPConnectionItem object) {
                return METADATA_SAPCONNECTIONS;
            }

            @Override
            public Object caseDelimitedFileConnectionItem(DelimitedFileConnectionItem object) {
                return METADATA_FILE_DELIMITED;
            }

            @Override
            public Object casePositionalFileConnectionItem(PositionalFileConnectionItem object) {
                return METADATA_FILE_POSITIONAL;
            }

            @Override
            public Object caseRegExFileConnectionItem(RegExFileConnectionItem object) {
                return METADATA_FILE_REGEXP;
            }

            @Override
            public Object caseXmlFileConnectionItem(XmlFileConnectionItem object) {
                return METADATA_FILE_XML;
            }

            @Override
            public Object caseExcelFileConnectionItem(ExcelFileConnectionItem object) {
                return METADATA_FILE_EXCEL;
            }

            @Override
            public Object caseLdifFileConnectionItem(LdifFileConnectionItem object) {
                return METADATA_FILE_LDIF;
            }

            @Override
            public Object caseLDAPSchemaConnectionItem(LDAPSchemaConnectionItem object) {
                return METADATA_LDAP_SCHEMA;
            }

            @Override
            public Object caseGenericSchemaConnectionItem(GenericSchemaConnectionItem object) {
                return METADATA_GENERIC_SCHEMA;
            }

            @Override
            public Object caseSalesforceSchemaConnectionItem(SalesforceSchemaConnectionItem object) {
                return METADATA_SALESFORCE_SCHEMA;
            }

            @Override
            public Object caseWSDLSchemaConnectionItem(WSDLSchemaConnectionItem object) {
                return METADATA_WSDL_SCHEMA;
            }

            @Override
            public Object caseEDIFACTConnectionItem(EDIFACTConnectionItem object) {
                return METADATA_EDIFACT;
            }

            @Override
            public Object caseEbcdicConnectionItem(EbcdicConnectionItem object) {
                return METADATA_FILE_EBCDIC;
            }

            @Override
            public Object caseHL7ConnectionItem(HL7ConnectionItem object) {
                return METADATA_FILE_HL7;
            }

            @Override
            public Object caseFTPConnectionItem(FTPConnectionItem object) {
                return METADATA_FILE_FTP;
            }

            @Override
            public Object caseBRMSConnectionItem(BRMSConnectionItem object) {
                return METADATA_FILE_BRMS;
            }

            @Override
            public Object caseMDMConnectionItem(MDMConnectionItem object) {
                return METADATA_MDMCONNECTION;
            }

            @Override
            public Object caseSVGBusinessProcessItem(SVGBusinessProcessItem object) {
                return SVG_BUSINESS_PROCESS;
            }

            @Override
            public Object caseHeaderFooterConnectionItem(HeaderFooterConnectionItem object) {
                return METADATA_HEADER_FOOTER;
            }

            // MOD mzhao feature 9207
            @Override
            public Object caseTDQItem(TDQItem object) {
                return TDQ_ELEMENT;
            }

            @Override
            public Object caseValidationRulesConnectionItem(ValidationRulesConnectionItem object) {
                return METADATA_VALIDATION_RULES;
            }

            @Override
            public Object defaultCase(EObject object) {
                // throw new IllegalStateException();
                return null; // fixed for TUP-575
            }
        }.doSwitch(item);

    }

    private static ERepositoryObjectType getTDQRepObjType(Item item) {
        AbstractDQModelService dqModelService = CoreRuntimePlugin.getInstance().getDQModelService();
        if (dqModelService != null) {
            return dqModelService.getTDQRepObjType(item);
        }
        return null;
    }

    private static ERepositoryObjectType getRepositoryObjectType(Item item) {
        ERepositoryObjectType type = null;
        for (IRepositoryContentHandler handler : RepositoryContentManager.getHandlers()) {
            type = handler.getRepositoryObjectType(item);
            if (type != null) {
                break;
            }
        }
        return type;
    }

    public boolean isSubItem() {
        return this.subItem;
    }

    public boolean isResourceItem() {
        return this.isResouce();
    }

    /**
     * DOC bZhou Comment method "isDQItemType".
     * 
     * This method is to estimat a type is a TDQ item or not.
     * 
     * @return
     */
    public boolean isDQItemType() {
        return Arrays.asList(this.getProducts()).contains(PROD_DQ);
    }

    /**
     * DOC bZhou Comment method "isDIType".
     * 
     * This method is to estimat a type belongs to Data Intergration.
     * 
     * @param type
     * @return
     */
    public boolean isDIItemType() {
        return Arrays.asList(this.getProducts()).contains(PROD_DI);
    }

    /**
     * DOC bZhou Comment method "isSharedType".
     * 
     * This method is to estimat a type belongs to both DQ and DI product.
     * 
     * @param type
     * @return
     */
    public boolean isSharedType() {
        return isDQItemType() && isDIItemType();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.repository.DynaEnum#name()
     */
    @Override
    public String name() {
        if (isStaticNode()) {
            Field[] allFields = ERepositoryObjectType.class.getDeclaredFields();
            for (Field f : allFields) {
                try {
                    Object object = f.get(null);
                    if (object == this) {
                        return f.getName();
                    }
                } catch (IllegalArgumentException e) {
                    //
                } catch (IllegalAccessException e) {
                    //
                }
            }
        }
        return super.name();
    }

    public String getFolder() {
        return this.folder;
    }

    public String[] getProducts() {
        return this.products;
    }

    public boolean isResouce() {
        return this.isResouce;
    }

    public String[] getUserRight() {
        return userRight;
    }

    public String getNamePattern() {
        return namePattern;
    }

    public boolean isAllowMultiName() {
        return isAllowMultiName;
    }

    public boolean isAllowPlainFolder() {
        return isAllowPlainFolder;
    }

    public void setAllowPlainFolder(boolean isAllowPlainFolder) {
        this.isAllowPlainFolder = isAllowPlainFolder;
    }

    public boolean addExtraProducts(String[] productsArray) {
        if (productsArray != null && productsArray.length > 0) {
            if (this.products == null && this.products.length == 0) {
                this.products = productsArray;
            } else {
                String[] tmp = new String[this.products.length + productsArray.length];
                System.arraycopy(this.products, 0, tmp, 0, this.products.length);
                System.arraycopy(productsArray, 0, tmp, this.products.length, productsArray.length);
                this.products = tmp;
            }
            return true;
        }
        return false;
    }

    protected List<ERepositoryObjectType> getParentTypes() {
        return this.parentTypes;
    }

    protected List<ERepositoryObjectType> getChildrenTypes() {
        return this.childrenTypes;
    }

    public ERepositoryObjectType[] getParentTypesArray() {
        return this.parentTypes.toArray(new ERepositoryObjectType[0]);
    }

    public ERepositoryObjectType[] getChildrenTypesArray() {
        return this.childrenTypes.toArray(new ERepositoryObjectType[0]);
    }

    public void setAParent(ERepositoryObjectType parentType) {
        if (parentType != null) {
            getParentTypes().add(parentType);
            parentType.getChildrenTypes().add(this);
        }
    }

    public boolean isChildTypeOf(ERepositoryObjectType parentType) {
        if (parentType != null && hasParentType()) {
            return getParentTypes().contains(parentType);
        }
        return false;
    }

    public boolean isParentTypeOf(ERepositoryObjectType childType) {
        if (childType != null && hasChildrenType()) {
            return getChildrenTypes().contains(childType);
        }
        return false;
    }

    public boolean hasChildrenType() {
        return !getChildrenTypes().isEmpty();
    }

    public boolean hasParentType() {
        return !getParentTypes().isEmpty();
    }

    /**
     * 
     * DOC ggu Comment method "valueOf".
     * 
     * in order to adapte old codes. and enable to init this class at same times.
     * 
     * @param clazz
     * @param name
     * @return
     */
    public static ERepositoryObjectType valueOf(Class<ERepositoryObjectType> clazz, String name) {
        return DynaEnum.valueOf(clazz, name);
    }

    public static ERepositoryObjectType valueOf(String name) {
        return valueOf(ERepositoryObjectType.class, name);
    }

    public static ERepositoryObjectType findParentType(ERepositoryObjectType curtype) {
        if (curtype == null) {
            return null;
        }
        ERepositoryObjectType parentType = null;
        ERepositoryObjectType[] parentTypesArray = curtype.getParentTypesArray();
        if (parentTypesArray != null && parentTypesArray.length > 0) {
            parentType = parentTypesArray[0]; // only process first parent.
        }
        // maybe find by path again
        if (parentType == null) {
            String folder = curtype.getFolder();
            if (StringUtils.isNotEmpty(folder)) {
                IPath folderPath = new Path(folder);
                parentType = findParentTypeByPath(folderPath);
            }
        }
        return parentType;
    }

    private static ERepositoryObjectType findParentTypeByPath(IPath folderPath) {
        if (folderPath != null && folderPath.segmentCount() > 1) { // if only 1, should be no parent
            IPath parentPath = folderPath.removeLastSegments(1);
            for (ERepositoryObjectType type : (ERepositoryObjectType[]) ERepositoryObjectType.values()) {
                if (StringUtils.isNotEmpty(type.getFolder()) && parentPath.equals(new Path(type.getFolder()))) {
                    return type;
                }
            }
            return findParentTypeByPath(parentPath);
        }
        return null;
    }

    public static List<ERepositoryObjectType> getAllTypesOfProcess() {
        List<ERepositoryObjectType> allTypes = new ArrayList<ERepositoryObjectType>();

        if (ERepositoryObjectType.PROCESS != null) {
            allTypes.add(ERepositoryObjectType.PROCESS);
        }
        if (ERepositoryObjectType.PROCESS_MR != null) {
            allTypes.add(ERepositoryObjectType.PROCESS_MR);
        }
        if (ERepositoryObjectType.PROCESS_STORM != null) {
            allTypes.add(ERepositoryObjectType.PROCESS_STORM);
        }

        return allTypes;
    }
}
