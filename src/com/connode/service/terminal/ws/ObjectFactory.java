
package com.connode.service.terminal.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.connode.service.terminal.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RemoveNodeIdentityFromWhiteList_QNAME = new QName("http://ws.terminal.service.connode.com/", "removeNodeIdentityFromWhiteList");
    private final static QName _RestartNodeResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "restartNodeResponse");
    private final static QName _NodeToServerDataRateResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "nodeToServerDataRateResponse");
    private final static QName _CountNodes_QNAME = new QName("http://ws.terminal.service.connode.com/", "countNodes");
    private final static QName _SearchNodesResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "searchNodesResponse");
    private final static QName _GetAreaTopologyViewResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "getAreaTopologyViewResponse");
    private final static QName _GetUplinkIpTunnelInfoResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "getUplinkIpTunnelInfoResponse");
    private final static QName _PingNodeResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "pingNodeResponse");
    private final static QName _GetNodeById_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeById");
    private final static QName _RemoveNodeIdentityFromWhiteListResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "removeNodeIdentityFromWhiteListResponse");
    private final static QName _CountNodeMetrics_QNAME = new QName("http://ws.terminal.service.connode.com/", "countNodeMetrics");
    private final static QName _SetLocationResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "setLocationResponse");
    private final static QName _GetMessageQueuePullIntervalForNodeResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "getMessageQueuePullIntervalForNodeResponse");
    private final static QName _PurgeNode_QNAME = new QName("http://ws.terminal.service.connode.com/", "purgeNode");
    private final static QName _ServerToNodeDataRateResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "serverToNodeDataRateResponse");
    private final static QName _UplinkIpTunneInfoResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "uplinkIpTunneInfoResponse");
    private final static QName _CountNodeEventsResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "countNodeEventsResponse");
    private final static QName _SetMessageQueuePullIntervalForNode_QNAME = new QName("http://ws.terminal.service.connode.com/", "setMessageQueuePullIntervalForNode");
    private final static QName _AddNodeIdentityToBlackList_QNAME = new QName("http://ws.terminal.service.connode.com/", "addNodeIdentityToBlackList");
    private final static QName _GetNodeMetrics_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeMetrics");
    private final static QName _IsNodeIdentityBlackListed_QNAME = new QName("http://ws.terminal.service.connode.com/", "isNodeIdentityBlackListed");
    private final static QName _RemoveNodeIdentityFromBlackList_QNAME = new QName("http://ws.terminal.service.connode.com/", "removeNodeIdentityFromBlackList");
    private final static QName _SetNodeAccessConfigurationResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "setNodeAccessConfigurationResponse");
    private final static QName _GetNodeEvents_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeEvents");
    private final static QName _SearchNodes_QNAME = new QName("http://ws.terminal.service.connode.com/", "searchNodes");
    private final static QName _GetNodeMetricsResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeMetricsResponse");
    private final static QName _PurgeNodeResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "purgeNodeResponse");
    private final static QName _ResetMessageQueuePullIntervalForNodeResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "resetMessageQueuePullIntervalForNodeResponse");
    private final static QName _CountNodeEvents_QNAME = new QName("http://ws.terminal.service.connode.com/", "countNodeEvents");
    private final static QName _GetNodeAccessConfigurationResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeAccessConfigurationResponse");
    private final static QName _GetNodeAccessConfiguration_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeAccessConfiguration");
    private final static QName _GetAreaTopologyView_QNAME = new QName("http://ws.terminal.service.connode.com/", "getAreaTopologyView");
    private final static QName _RemoveNodeIdentityFromBlackListResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "removeNodeIdentityFromBlackListResponse");
    private final static QName _SearchNodeEventsResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "searchNodeEventsResponse");
    private final static QName _SearchNodeMetrics_QNAME = new QName("http://ws.terminal.service.connode.com/", "searchNodeMetrics");
    private final static QName _AddNodeIdentityToWhiteList_QNAME = new QName("http://ws.terminal.service.connode.com/", "addNodeIdentityToWhiteList");
    private final static QName _ExecuteServerToNodeDataRate_QNAME = new QName("http://ws.terminal.service.connode.com/", "executeServerToNodeDataRate");
    private final static QName _ResetMessageQueuePullIntervalForNode_QNAME = new QName("http://ws.terminal.service.connode.com/", "resetMessageQueuePullIntervalForNode");
    private final static QName _IsNodeIdentityWhiteListed_QNAME = new QName("http://ws.terminal.service.connode.com/", "isNodeIdentityWhiteListed");
    private final static QName _SetMessageQueuePullIntervalForNodeResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "setMessageQueuePullIntervalForNodeResponse");
    private final static QName _PingNode_QNAME = new QName("http://ws.terminal.service.connode.com/", "pingNode");
    private final static QName _SearchNodeMetricsResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "searchNodeMetricsResponse");
    private final static QName _GetNodeTopologyView_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeTopologyView");
    private final static QName _CountNodeMetricsResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "countNodeMetricsResponse");
    private final static QName _GetNodeByIdResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeByIdResponse");
    private final static QName _GetMessageQueuePullIntervalForNode_QNAME = new QName("http://ws.terminal.service.connode.com/", "getMessageQueuePullIntervalForNode");
    private final static QName _NodeFault_QNAME = new QName("http://ws.terminal.service.connode.com/", "NodeFault");
    private final static QName _CountNodesResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "countNodesResponse");
    private final static QName _IsNodeIdentityWhiteListedResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "isNodeIdentityWhiteListedResponse");
    private final static QName _RestartNode_QNAME = new QName("http://ws.terminal.service.connode.com/", "restartNode");
    private final static QName _SetLocation_QNAME = new QName("http://ws.terminal.service.connode.com/", "setLocation");
    private final static QName _SearchNodeEvents_QNAME = new QName("http://ws.terminal.service.connode.com/", "searchNodeEvents");
    private final static QName _GetNodeTopologyViewResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeTopologyViewResponse");
    private final static QName _AddNodeIdentityToWhiteListResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "addNodeIdentityToWhiteListResponse");
    private final static QName _AddNodeIdentityToBlackListResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "addNodeIdentityToBlackListResponse");
    private final static QName _ExecuteNodeToServerDataRate_QNAME = new QName("http://ws.terminal.service.connode.com/", "executeNodeToServerDataRate");
    private final static QName _SetNodeAccessConfiguration_QNAME = new QName("http://ws.terminal.service.connode.com/", "setNodeAccessConfiguration");
    private final static QName _GetNodeEventsResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "getNodeEventsResponse");
    private final static QName _IsNodeIdentityBlackListedResponse_QNAME = new QName("http://ws.terminal.service.connode.com/", "isNodeIdentityBlackListedResponse");
    private final static QName _GetUplinkIpTunnelInfo_QNAME = new QName("http://ws.terminal.service.connode.com/", "getUplinkIpTunnelInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.connode.service.terminal.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetNodeEvents }
     * 
     */
    public GetNodeEvents createGetNodeEvents() {
        return new GetNodeEvents();
    }

    /**
     * Create an instance of {@link SearchNodes }
     * 
     */
    public SearchNodes createSearchNodes() {
        return new SearchNodes();
    }

    /**
     * Create an instance of {@link ResetMessageQueuePullIntervalForNodeResponse }
     * 
     */
    public ResetMessageQueuePullIntervalForNodeResponse createResetMessageQueuePullIntervalForNodeResponse() {
        return new ResetMessageQueuePullIntervalForNodeResponse();
    }

    /**
     * Create an instance of {@link GetNodeMetricsResponse }
     * 
     */
    public GetNodeMetricsResponse createGetNodeMetricsResponse() {
        return new GetNodeMetricsResponse();
    }

    /**
     * Create an instance of {@link PurgeNodeResponse }
     * 
     */
    public PurgeNodeResponse createPurgeNodeResponse() {
        return new PurgeNodeResponse();
    }

    /**
     * Create an instance of {@link CountNodeEvents }
     * 
     */
    public CountNodeEvents createCountNodeEvents() {
        return new CountNodeEvents();
    }

    /**
     * Create an instance of {@link GetNodeAccessConfigurationResponse }
     * 
     */
    public GetNodeAccessConfigurationResponse createGetNodeAccessConfigurationResponse() {
        return new GetNodeAccessConfigurationResponse();
    }

    /**
     * Create an instance of {@link GetNodeAccessConfiguration }
     * 
     */
    public GetNodeAccessConfiguration createGetNodeAccessConfiguration() {
        return new GetNodeAccessConfiguration();
    }

    /**
     * Create an instance of {@link SetLocationResponse }
     * 
     */
    public SetLocationResponse createSetLocationResponse() {
        return new SetLocationResponse();
    }

    /**
     * Create an instance of {@link GetMessageQueuePullIntervalForNodeResponse }
     * 
     */
    public GetMessageQueuePullIntervalForNodeResponse createGetMessageQueuePullIntervalForNodeResponse() {
        return new GetMessageQueuePullIntervalForNodeResponse();
    }

    /**
     * Create an instance of {@link PurgeNode }
     * 
     */
    public PurgeNode createPurgeNode() {
        return new PurgeNode();
    }

    /**
     * Create an instance of {@link WsServerToNodeDataRateResponse }
     * 
     */
    public WsServerToNodeDataRateResponse createWsServerToNodeDataRateResponse() {
        return new WsServerToNodeDataRateResponse();
    }

    /**
     * Create an instance of {@link AddNodeIdentityToBlackList }
     * 
     */
    public AddNodeIdentityToBlackList createAddNodeIdentityToBlackList() {
        return new AddNodeIdentityToBlackList();
    }

    /**
     * Create an instance of {@link GetNodeMetrics }
     * 
     */
    public GetNodeMetrics createGetNodeMetrics() {
        return new GetNodeMetrics();
    }

    /**
     * Create an instance of {@link IsNodeIdentityBlackListed }
     * 
     */
    public IsNodeIdentityBlackListed createIsNodeIdentityBlackListed() {
        return new IsNodeIdentityBlackListed();
    }

    /**
     * Create an instance of {@link RemoveNodeIdentityFromBlackList }
     * 
     */
    public RemoveNodeIdentityFromBlackList createRemoveNodeIdentityFromBlackList() {
        return new RemoveNodeIdentityFromBlackList();
    }

    /**
     * Create an instance of {@link SetNodeAccessConfigurationResponse }
     * 
     */
    public SetNodeAccessConfigurationResponse createSetNodeAccessConfigurationResponse() {
        return new SetNodeAccessConfigurationResponse();
    }

    /**
     * Create an instance of {@link WsUplinkIpTunnelInfoResponse }
     * 
     */
    public WsUplinkIpTunnelInfoResponse createWsUplinkIpTunnelInfoResponse() {
        return new WsUplinkIpTunnelInfoResponse();
    }

    /**
     * Create an instance of {@link CountNodeEventsResponse }
     * 
     */
    public CountNodeEventsResponse createCountNodeEventsResponse() {
        return new CountNodeEventsResponse();
    }

    /**
     * Create an instance of {@link SetMessageQueuePullIntervalForNode }
     * 
     */
    public SetMessageQueuePullIntervalForNode createSetMessageQueuePullIntervalForNode() {
        return new SetMessageQueuePullIntervalForNode();
    }

    /**
     * Create an instance of {@link SearchNodesResponse }
     * 
     */
    public SearchNodesResponse createSearchNodesResponse() {
        return new SearchNodesResponse();
    }

    /**
     * Create an instance of {@link GetNodeById }
     * 
     */
    public GetNodeById createGetNodeById() {
        return new GetNodeById();
    }

    /**
     * Create an instance of {@link GetAreaTopologyViewResponse }
     * 
     */
    public GetAreaTopologyViewResponse createGetAreaTopologyViewResponse() {
        return new GetAreaTopologyViewResponse();
    }

    /**
     * Create an instance of {@link WsPingNodeResponse }
     * 
     */
    public WsPingNodeResponse createWsPingNodeResponse() {
        return new WsPingNodeResponse();
    }

    /**
     * Create an instance of {@link CountNodeMetrics }
     * 
     */
    public CountNodeMetrics createCountNodeMetrics() {
        return new CountNodeMetrics();
    }

    /**
     * Create an instance of {@link RemoveNodeIdentityFromWhiteListResponse }
     * 
     */
    public RemoveNodeIdentityFromWhiteListResponse createRemoveNodeIdentityFromWhiteListResponse() {
        return new RemoveNodeIdentityFromWhiteListResponse();
    }

    /**
     * Create an instance of {@link RemoveNodeIdentityFromWhiteList }
     * 
     */
    public RemoveNodeIdentityFromWhiteList createRemoveNodeIdentityFromWhiteList() {
        return new RemoveNodeIdentityFromWhiteList();
    }

    /**
     * Create an instance of {@link WsRestartNodeResponse }
     * 
     */
    public WsRestartNodeResponse createWsRestartNodeResponse() {
        return new WsRestartNodeResponse();
    }

    /**
     * Create an instance of {@link WsNodeToServerDataRateResponse }
     * 
     */
    public WsNodeToServerDataRateResponse createWsNodeToServerDataRateResponse() {
        return new WsNodeToServerDataRateResponse();
    }

    /**
     * Create an instance of {@link CountNodes }
     * 
     */
    public CountNodes createCountNodes() {
        return new CountNodes();
    }

    /**
     * Create an instance of {@link AddNodeIdentityToBlackListResponse }
     * 
     */
    public AddNodeIdentityToBlackListResponse createAddNodeIdentityToBlackListResponse() {
        return new AddNodeIdentityToBlackListResponse();
    }

    /**
     * Create an instance of {@link ExecuteNodeToServerDataRate }
     * 
     */
    public ExecuteNodeToServerDataRate createExecuteNodeToServerDataRate() {
        return new ExecuteNodeToServerDataRate();
    }

    /**
     * Create an instance of {@link AddNodeIdentityToWhiteListResponse }
     * 
     */
    public AddNodeIdentityToWhiteListResponse createAddNodeIdentityToWhiteListResponse() {
        return new AddNodeIdentityToWhiteListResponse();
    }

    /**
     * Create an instance of {@link SetNodeAccessConfiguration }
     * 
     */
    public SetNodeAccessConfiguration createSetNodeAccessConfiguration() {
        return new SetNodeAccessConfiguration();
    }

    /**
     * Create an instance of {@link GetUplinkIpTunnelInfo }
     * 
     */
    public GetUplinkIpTunnelInfo createGetUplinkIpTunnelInfo() {
        return new GetUplinkIpTunnelInfo();
    }

    /**
     * Create an instance of {@link GetNodeEventsResponse }
     * 
     */
    public GetNodeEventsResponse createGetNodeEventsResponse() {
        return new GetNodeEventsResponse();
    }

    /**
     * Create an instance of {@link IsNodeIdentityBlackListedResponse }
     * 
     */
    public IsNodeIdentityBlackListedResponse createIsNodeIdentityBlackListedResponse() {
        return new IsNodeIdentityBlackListedResponse();
    }

    /**
     * Create an instance of {@link IsNodeIdentityWhiteListedResponse }
     * 
     */
    public IsNodeIdentityWhiteListedResponse createIsNodeIdentityWhiteListedResponse() {
        return new IsNodeIdentityWhiteListedResponse();
    }

    /**
     * Create an instance of {@link RestartNode }
     * 
     */
    public RestartNode createRestartNode() {
        return new RestartNode();
    }

    /**
     * Create an instance of {@link SetLocation }
     * 
     */
    public SetLocation createSetLocation() {
        return new SetLocation();
    }

    /**
     * Create an instance of {@link CountNodesResponse }
     * 
     */
    public CountNodesResponse createCountNodesResponse() {
        return new CountNodesResponse();
    }

    /**
     * Create an instance of {@link SearchNodeEvents }
     * 
     */
    public SearchNodeEvents createSearchNodeEvents() {
        return new SearchNodeEvents();
    }

    /**
     * Create an instance of {@link GetNodeTopologyViewResponse }
     * 
     */
    public GetNodeTopologyViewResponse createGetNodeTopologyViewResponse() {
        return new GetNodeTopologyViewResponse();
    }

    /**
     * Create an instance of {@link IsNodeIdentityWhiteListed }
     * 
     */
    public IsNodeIdentityWhiteListed createIsNodeIdentityWhiteListed() {
        return new IsNodeIdentityWhiteListed();
    }

    /**
     * Create an instance of {@link SetMessageQueuePullIntervalForNodeResponse }
     * 
     */
    public SetMessageQueuePullIntervalForNodeResponse createSetMessageQueuePullIntervalForNodeResponse() {
        return new SetMessageQueuePullIntervalForNodeResponse();
    }

    /**
     * Create an instance of {@link PingNode }
     * 
     */
    public PingNode createPingNode() {
        return new PingNode();
    }

    /**
     * Create an instance of {@link SearchNodeMetricsResponse }
     * 
     */
    public SearchNodeMetricsResponse createSearchNodeMetricsResponse() {
        return new SearchNodeMetricsResponse();
    }

    /**
     * Create an instance of {@link GetNodeTopologyView }
     * 
     */
    public GetNodeTopologyView createGetNodeTopologyView() {
        return new GetNodeTopologyView();
    }

    /**
     * Create an instance of {@link CountNodeMetricsResponse }
     * 
     */
    public CountNodeMetricsResponse createCountNodeMetricsResponse() {
        return new CountNodeMetricsResponse();
    }

    /**
     * Create an instance of {@link GetNodeByIdResponse }
     * 
     */
    public GetNodeByIdResponse createGetNodeByIdResponse() {
        return new GetNodeByIdResponse();
    }

    /**
     * Create an instance of {@link GetMessageQueuePullIntervalForNode }
     * 
     */
    public GetMessageQueuePullIntervalForNode createGetMessageQueuePullIntervalForNode() {
        return new GetMessageQueuePullIntervalForNode();
    }

    /**
     * Create an instance of {@link WsNodeFault }
     * 
     */
    public WsNodeFault createWsNodeFault() {
        return new WsNodeFault();
    }

    /**
     * Create an instance of {@link GetAreaTopologyView }
     * 
     */
    public GetAreaTopologyView createGetAreaTopologyView() {
        return new GetAreaTopologyView();
    }

    /**
     * Create an instance of {@link RemoveNodeIdentityFromBlackListResponse }
     * 
     */
    public RemoveNodeIdentityFromBlackListResponse createRemoveNodeIdentityFromBlackListResponse() {
        return new RemoveNodeIdentityFromBlackListResponse();
    }

    /**
     * Create an instance of {@link SearchNodeEventsResponse }
     * 
     */
    public SearchNodeEventsResponse createSearchNodeEventsResponse() {
        return new SearchNodeEventsResponse();
    }

    /**
     * Create an instance of {@link SearchNodeMetrics }
     * 
     */
    public SearchNodeMetrics createSearchNodeMetrics() {
        return new SearchNodeMetrics();
    }

    /**
     * Create an instance of {@link ResetMessageQueuePullIntervalForNode }
     * 
     */
    public ResetMessageQueuePullIntervalForNode createResetMessageQueuePullIntervalForNode() {
        return new ResetMessageQueuePullIntervalForNode();
    }

    /**
     * Create an instance of {@link AddNodeIdentityToWhiteList }
     * 
     */
    public AddNodeIdentityToWhiteList createAddNodeIdentityToWhiteList() {
        return new AddNodeIdentityToWhiteList();
    }

    /**
     * Create an instance of {@link ExecuteServerToNodeDataRate }
     * 
     */
    public ExecuteServerToNodeDataRate createExecuteServerToNodeDataRate() {
        return new ExecuteServerToNodeDataRate();
    }

    /**
     * Create an instance of {@link WsRestart }
     * 
     */
    public WsRestart createWsRestart() {
        return new WsRestart();
    }

    /**
     * Create an instance of {@link WsNodeMetricSearchResult }
     * 
     */
    public WsNodeMetricSearchResult createWsNodeMetricSearchResult() {
        return new WsNodeMetricSearchResult();
    }

    /**
     * Create an instance of {@link WsNodeMetric }
     * 
     */
    public WsNodeMetric createWsNodeMetric() {
        return new WsNodeMetric();
    }

    /**
     * Create an instance of {@link WsNodeTopology }
     * 
     */
    public WsNodeTopology createWsNodeTopology() {
        return new WsNodeTopology();
    }

    /**
     * Create an instance of {@link WsServerToNodeDataRateParameters }
     * 
     */
    public WsServerToNodeDataRateParameters createWsServerToNodeDataRateParameters() {
        return new WsServerToNodeDataRateParameters();
    }

    /**
     * Create an instance of {@link WsUplinkIpTunnelInfo }
     * 
     */
    public WsUplinkIpTunnelInfo createWsUplinkIpTunnelInfo() {
        return new WsUplinkIpTunnelInfo();
    }

    /**
     * Create an instance of {@link WsNodeSearch }
     * 
     */
    public WsNodeSearch createWsNodeSearch() {
        return new WsNodeSearch();
    }

    /**
     * Create an instance of {@link WsNodeEventSearchCriteria }
     * 
     */
    public WsNodeEventSearchCriteria createWsNodeEventSearchCriteria() {
        return new WsNodeEventSearchCriteria();
    }

    /**
     * Create an instance of {@link WsNodeMetricSearchCriteria }
     * 
     */
    public WsNodeMetricSearchCriteria createWsNodeMetricSearchCriteria() {
        return new WsNodeMetricSearchCriteria();
    }

    /**
     * Create an instance of {@link WsEvent }
     * 
     */
    public WsEvent createWsEvent() {
        return new WsEvent();
    }

    /**
     * Create an instance of {@link WsNodeAccessConfiguration }
     * 
     */
    public WsNodeAccessConfiguration createWsNodeAccessConfiguration() {
        return new WsNodeAccessConfiguration();
    }

    /**
     * Create an instance of {@link WsNodeTopologyView }
     * 
     */
    public WsNodeTopologyView createWsNodeTopologyView() {
        return new WsNodeTopologyView();
    }

    /**
     * Create an instance of {@link WsPing }
     * 
     */
    public WsPing createWsPing() {
        return new WsPing();
    }

    /**
     * Create an instance of {@link WsNode }
     * 
     */
    public WsNode createWsNode() {
        return new WsNode();
    }

    /**
     * Create an instance of {@link WsNodeEventSearchResult }
     * 
     */
    public WsNodeEventSearchResult createWsNodeEventSearchResult() {
        return new WsNodeEventSearchResult();
    }

    /**
     * Create an instance of {@link WsNodeSearchResult }
     * 
     */
    public WsNodeSearchResult createWsNodeSearchResult() {
        return new WsNodeSearchResult();
    }

    /**
     * Create an instance of {@link WsNodeToServerDataRateParameters }
     * 
     */
    public WsNodeToServerDataRateParameters createWsNodeToServerDataRateParameters() {
        return new WsNodeToServerDataRateParameters();
    }

    /**
     * Create an instance of {@link WsDataRateResult }
     * 
     */
    public WsDataRateResult createWsDataRateResult() {
        return new WsDataRateResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveNodeIdentityFromWhiteList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "removeNodeIdentityFromWhiteList")
    public JAXBElement<RemoveNodeIdentityFromWhiteList> createRemoveNodeIdentityFromWhiteList(RemoveNodeIdentityFromWhiteList value) {
        return new JAXBElement<RemoveNodeIdentityFromWhiteList>(_RemoveNodeIdentityFromWhiteList_QNAME, RemoveNodeIdentityFromWhiteList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsRestartNodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "restartNodeResponse")
    public JAXBElement<WsRestartNodeResponse> createRestartNodeResponse(WsRestartNodeResponse value) {
        return new JAXBElement<WsRestartNodeResponse>(_RestartNodeResponse_QNAME, WsRestartNodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsNodeToServerDataRateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "nodeToServerDataRateResponse")
    public JAXBElement<WsNodeToServerDataRateResponse> createNodeToServerDataRateResponse(WsNodeToServerDataRateResponse value) {
        return new JAXBElement<WsNodeToServerDataRateResponse>(_NodeToServerDataRateResponse_QNAME, WsNodeToServerDataRateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountNodes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "countNodes")
    public JAXBElement<CountNodes> createCountNodes(CountNodes value) {
        return new JAXBElement<CountNodes>(_CountNodes_QNAME, CountNodes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchNodesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "searchNodesResponse")
    public JAXBElement<SearchNodesResponse> createSearchNodesResponse(SearchNodesResponse value) {
        return new JAXBElement<SearchNodesResponse>(_SearchNodesResponse_QNAME, SearchNodesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAreaTopologyViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getAreaTopologyViewResponse")
    public JAXBElement<GetAreaTopologyViewResponse> createGetAreaTopologyViewResponse(GetAreaTopologyViewResponse value) {
        return new JAXBElement<GetAreaTopologyViewResponse>(_GetAreaTopologyViewResponse_QNAME, GetAreaTopologyViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsUplinkIpTunnelInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getUplinkIpTunnelInfoResponse")
    public JAXBElement<WsUplinkIpTunnelInfoResponse> createGetUplinkIpTunnelInfoResponse(WsUplinkIpTunnelInfoResponse value) {
        return new JAXBElement<WsUplinkIpTunnelInfoResponse>(_GetUplinkIpTunnelInfoResponse_QNAME, WsUplinkIpTunnelInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsPingNodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "pingNodeResponse")
    public JAXBElement<WsPingNodeResponse> createPingNodeResponse(WsPingNodeResponse value) {
        return new JAXBElement<WsPingNodeResponse>(_PingNodeResponse_QNAME, WsPingNodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeById")
    public JAXBElement<GetNodeById> createGetNodeById(GetNodeById value) {
        return new JAXBElement<GetNodeById>(_GetNodeById_QNAME, GetNodeById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveNodeIdentityFromWhiteListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "removeNodeIdentityFromWhiteListResponse")
    public JAXBElement<RemoveNodeIdentityFromWhiteListResponse> createRemoveNodeIdentityFromWhiteListResponse(RemoveNodeIdentityFromWhiteListResponse value) {
        return new JAXBElement<RemoveNodeIdentityFromWhiteListResponse>(_RemoveNodeIdentityFromWhiteListResponse_QNAME, RemoveNodeIdentityFromWhiteListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountNodeMetrics }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "countNodeMetrics")
    public JAXBElement<CountNodeMetrics> createCountNodeMetrics(CountNodeMetrics value) {
        return new JAXBElement<CountNodeMetrics>(_CountNodeMetrics_QNAME, CountNodeMetrics.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetLocationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "setLocationResponse")
    public JAXBElement<SetLocationResponse> createSetLocationResponse(SetLocationResponse value) {
        return new JAXBElement<SetLocationResponse>(_SetLocationResponse_QNAME, SetLocationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMessageQueuePullIntervalForNodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getMessageQueuePullIntervalForNodeResponse")
    public JAXBElement<GetMessageQueuePullIntervalForNodeResponse> createGetMessageQueuePullIntervalForNodeResponse(GetMessageQueuePullIntervalForNodeResponse value) {
        return new JAXBElement<GetMessageQueuePullIntervalForNodeResponse>(_GetMessageQueuePullIntervalForNodeResponse_QNAME, GetMessageQueuePullIntervalForNodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurgeNode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "purgeNode")
    public JAXBElement<PurgeNode> createPurgeNode(PurgeNode value) {
        return new JAXBElement<PurgeNode>(_PurgeNode_QNAME, PurgeNode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsServerToNodeDataRateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "serverToNodeDataRateResponse")
    public JAXBElement<WsServerToNodeDataRateResponse> createServerToNodeDataRateResponse(WsServerToNodeDataRateResponse value) {
        return new JAXBElement<WsServerToNodeDataRateResponse>(_ServerToNodeDataRateResponse_QNAME, WsServerToNodeDataRateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsUplinkIpTunnelInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "uplinkIpTunneInfoResponse")
    public JAXBElement<WsUplinkIpTunnelInfoResponse> createUplinkIpTunneInfoResponse(WsUplinkIpTunnelInfoResponse value) {
        return new JAXBElement<WsUplinkIpTunnelInfoResponse>(_UplinkIpTunneInfoResponse_QNAME, WsUplinkIpTunnelInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountNodeEventsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "countNodeEventsResponse")
    public JAXBElement<CountNodeEventsResponse> createCountNodeEventsResponse(CountNodeEventsResponse value) {
        return new JAXBElement<CountNodeEventsResponse>(_CountNodeEventsResponse_QNAME, CountNodeEventsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetMessageQueuePullIntervalForNode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "setMessageQueuePullIntervalForNode")
    public JAXBElement<SetMessageQueuePullIntervalForNode> createSetMessageQueuePullIntervalForNode(SetMessageQueuePullIntervalForNode value) {
        return new JAXBElement<SetMessageQueuePullIntervalForNode>(_SetMessageQueuePullIntervalForNode_QNAME, SetMessageQueuePullIntervalForNode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNodeIdentityToBlackList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "addNodeIdentityToBlackList")
    public JAXBElement<AddNodeIdentityToBlackList> createAddNodeIdentityToBlackList(AddNodeIdentityToBlackList value) {
        return new JAXBElement<AddNodeIdentityToBlackList>(_AddNodeIdentityToBlackList_QNAME, AddNodeIdentityToBlackList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeMetrics }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeMetrics")
    public JAXBElement<GetNodeMetrics> createGetNodeMetrics(GetNodeMetrics value) {
        return new JAXBElement<GetNodeMetrics>(_GetNodeMetrics_QNAME, GetNodeMetrics.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsNodeIdentityBlackListed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "isNodeIdentityBlackListed")
    public JAXBElement<IsNodeIdentityBlackListed> createIsNodeIdentityBlackListed(IsNodeIdentityBlackListed value) {
        return new JAXBElement<IsNodeIdentityBlackListed>(_IsNodeIdentityBlackListed_QNAME, IsNodeIdentityBlackListed.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveNodeIdentityFromBlackList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "removeNodeIdentityFromBlackList")
    public JAXBElement<RemoveNodeIdentityFromBlackList> createRemoveNodeIdentityFromBlackList(RemoveNodeIdentityFromBlackList value) {
        return new JAXBElement<RemoveNodeIdentityFromBlackList>(_RemoveNodeIdentityFromBlackList_QNAME, RemoveNodeIdentityFromBlackList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetNodeAccessConfigurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "setNodeAccessConfigurationResponse")
    public JAXBElement<SetNodeAccessConfigurationResponse> createSetNodeAccessConfigurationResponse(SetNodeAccessConfigurationResponse value) {
        return new JAXBElement<SetNodeAccessConfigurationResponse>(_SetNodeAccessConfigurationResponse_QNAME, SetNodeAccessConfigurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeEvents }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeEvents")
    public JAXBElement<GetNodeEvents> createGetNodeEvents(GetNodeEvents value) {
        return new JAXBElement<GetNodeEvents>(_GetNodeEvents_QNAME, GetNodeEvents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchNodes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "searchNodes")
    public JAXBElement<SearchNodes> createSearchNodes(SearchNodes value) {
        return new JAXBElement<SearchNodes>(_SearchNodes_QNAME, SearchNodes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeMetricsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeMetricsResponse")
    public JAXBElement<GetNodeMetricsResponse> createGetNodeMetricsResponse(GetNodeMetricsResponse value) {
        return new JAXBElement<GetNodeMetricsResponse>(_GetNodeMetricsResponse_QNAME, GetNodeMetricsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurgeNodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "purgeNodeResponse")
    public JAXBElement<PurgeNodeResponse> createPurgeNodeResponse(PurgeNodeResponse value) {
        return new JAXBElement<PurgeNodeResponse>(_PurgeNodeResponse_QNAME, PurgeNodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetMessageQueuePullIntervalForNodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "resetMessageQueuePullIntervalForNodeResponse")
    public JAXBElement<ResetMessageQueuePullIntervalForNodeResponse> createResetMessageQueuePullIntervalForNodeResponse(ResetMessageQueuePullIntervalForNodeResponse value) {
        return new JAXBElement<ResetMessageQueuePullIntervalForNodeResponse>(_ResetMessageQueuePullIntervalForNodeResponse_QNAME, ResetMessageQueuePullIntervalForNodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountNodeEvents }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "countNodeEvents")
    public JAXBElement<CountNodeEvents> createCountNodeEvents(CountNodeEvents value) {
        return new JAXBElement<CountNodeEvents>(_CountNodeEvents_QNAME, CountNodeEvents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeAccessConfigurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeAccessConfigurationResponse")
    public JAXBElement<GetNodeAccessConfigurationResponse> createGetNodeAccessConfigurationResponse(GetNodeAccessConfigurationResponse value) {
        return new JAXBElement<GetNodeAccessConfigurationResponse>(_GetNodeAccessConfigurationResponse_QNAME, GetNodeAccessConfigurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeAccessConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeAccessConfiguration")
    public JAXBElement<GetNodeAccessConfiguration> createGetNodeAccessConfiguration(GetNodeAccessConfiguration value) {
        return new JAXBElement<GetNodeAccessConfiguration>(_GetNodeAccessConfiguration_QNAME, GetNodeAccessConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAreaTopologyView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getAreaTopologyView")
    public JAXBElement<GetAreaTopologyView> createGetAreaTopologyView(GetAreaTopologyView value) {
        return new JAXBElement<GetAreaTopologyView>(_GetAreaTopologyView_QNAME, GetAreaTopologyView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveNodeIdentityFromBlackListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "removeNodeIdentityFromBlackListResponse")
    public JAXBElement<RemoveNodeIdentityFromBlackListResponse> createRemoveNodeIdentityFromBlackListResponse(RemoveNodeIdentityFromBlackListResponse value) {
        return new JAXBElement<RemoveNodeIdentityFromBlackListResponse>(_RemoveNodeIdentityFromBlackListResponse_QNAME, RemoveNodeIdentityFromBlackListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchNodeEventsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "searchNodeEventsResponse")
    public JAXBElement<SearchNodeEventsResponse> createSearchNodeEventsResponse(SearchNodeEventsResponse value) {
        return new JAXBElement<SearchNodeEventsResponse>(_SearchNodeEventsResponse_QNAME, SearchNodeEventsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchNodeMetrics }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "searchNodeMetrics")
    public JAXBElement<SearchNodeMetrics> createSearchNodeMetrics(SearchNodeMetrics value) {
        return new JAXBElement<SearchNodeMetrics>(_SearchNodeMetrics_QNAME, SearchNodeMetrics.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNodeIdentityToWhiteList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "addNodeIdentityToWhiteList")
    public JAXBElement<AddNodeIdentityToWhiteList> createAddNodeIdentityToWhiteList(AddNodeIdentityToWhiteList value) {
        return new JAXBElement<AddNodeIdentityToWhiteList>(_AddNodeIdentityToWhiteList_QNAME, AddNodeIdentityToWhiteList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteServerToNodeDataRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "executeServerToNodeDataRate")
    public JAXBElement<ExecuteServerToNodeDataRate> createExecuteServerToNodeDataRate(ExecuteServerToNodeDataRate value) {
        return new JAXBElement<ExecuteServerToNodeDataRate>(_ExecuteServerToNodeDataRate_QNAME, ExecuteServerToNodeDataRate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetMessageQueuePullIntervalForNode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "resetMessageQueuePullIntervalForNode")
    public JAXBElement<ResetMessageQueuePullIntervalForNode> createResetMessageQueuePullIntervalForNode(ResetMessageQueuePullIntervalForNode value) {
        return new JAXBElement<ResetMessageQueuePullIntervalForNode>(_ResetMessageQueuePullIntervalForNode_QNAME, ResetMessageQueuePullIntervalForNode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsNodeIdentityWhiteListed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "isNodeIdentityWhiteListed")
    public JAXBElement<IsNodeIdentityWhiteListed> createIsNodeIdentityWhiteListed(IsNodeIdentityWhiteListed value) {
        return new JAXBElement<IsNodeIdentityWhiteListed>(_IsNodeIdentityWhiteListed_QNAME, IsNodeIdentityWhiteListed.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetMessageQueuePullIntervalForNodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "setMessageQueuePullIntervalForNodeResponse")
    public JAXBElement<SetMessageQueuePullIntervalForNodeResponse> createSetMessageQueuePullIntervalForNodeResponse(SetMessageQueuePullIntervalForNodeResponse value) {
        return new JAXBElement<SetMessageQueuePullIntervalForNodeResponse>(_SetMessageQueuePullIntervalForNodeResponse_QNAME, SetMessageQueuePullIntervalForNodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PingNode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "pingNode")
    public JAXBElement<PingNode> createPingNode(PingNode value) {
        return new JAXBElement<PingNode>(_PingNode_QNAME, PingNode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchNodeMetricsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "searchNodeMetricsResponse")
    public JAXBElement<SearchNodeMetricsResponse> createSearchNodeMetricsResponse(SearchNodeMetricsResponse value) {
        return new JAXBElement<SearchNodeMetricsResponse>(_SearchNodeMetricsResponse_QNAME, SearchNodeMetricsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeTopologyView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeTopologyView")
    public JAXBElement<GetNodeTopologyView> createGetNodeTopologyView(GetNodeTopologyView value) {
        return new JAXBElement<GetNodeTopologyView>(_GetNodeTopologyView_QNAME, GetNodeTopologyView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountNodeMetricsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "countNodeMetricsResponse")
    public JAXBElement<CountNodeMetricsResponse> createCountNodeMetricsResponse(CountNodeMetricsResponse value) {
        return new JAXBElement<CountNodeMetricsResponse>(_CountNodeMetricsResponse_QNAME, CountNodeMetricsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeByIdResponse")
    public JAXBElement<GetNodeByIdResponse> createGetNodeByIdResponse(GetNodeByIdResponse value) {
        return new JAXBElement<GetNodeByIdResponse>(_GetNodeByIdResponse_QNAME, GetNodeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMessageQueuePullIntervalForNode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getMessageQueuePullIntervalForNode")
    public JAXBElement<GetMessageQueuePullIntervalForNode> createGetMessageQueuePullIntervalForNode(GetMessageQueuePullIntervalForNode value) {
        return new JAXBElement<GetMessageQueuePullIntervalForNode>(_GetMessageQueuePullIntervalForNode_QNAME, GetMessageQueuePullIntervalForNode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsNodeFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "NodeFault")
    public JAXBElement<WsNodeFault> createNodeFault(WsNodeFault value) {
        return new JAXBElement<WsNodeFault>(_NodeFault_QNAME, WsNodeFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountNodesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "countNodesResponse")
    public JAXBElement<CountNodesResponse> createCountNodesResponse(CountNodesResponse value) {
        return new JAXBElement<CountNodesResponse>(_CountNodesResponse_QNAME, CountNodesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsNodeIdentityWhiteListedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "isNodeIdentityWhiteListedResponse")
    public JAXBElement<IsNodeIdentityWhiteListedResponse> createIsNodeIdentityWhiteListedResponse(IsNodeIdentityWhiteListedResponse value) {
        return new JAXBElement<IsNodeIdentityWhiteListedResponse>(_IsNodeIdentityWhiteListedResponse_QNAME, IsNodeIdentityWhiteListedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RestartNode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "restartNode")
    public JAXBElement<RestartNode> createRestartNode(RestartNode value) {
        return new JAXBElement<RestartNode>(_RestartNode_QNAME, RestartNode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetLocation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "setLocation")
    public JAXBElement<SetLocation> createSetLocation(SetLocation value) {
        return new JAXBElement<SetLocation>(_SetLocation_QNAME, SetLocation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchNodeEvents }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "searchNodeEvents")
    public JAXBElement<SearchNodeEvents> createSearchNodeEvents(SearchNodeEvents value) {
        return new JAXBElement<SearchNodeEvents>(_SearchNodeEvents_QNAME, SearchNodeEvents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeTopologyViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeTopologyViewResponse")
    public JAXBElement<GetNodeTopologyViewResponse> createGetNodeTopologyViewResponse(GetNodeTopologyViewResponse value) {
        return new JAXBElement<GetNodeTopologyViewResponse>(_GetNodeTopologyViewResponse_QNAME, GetNodeTopologyViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNodeIdentityToWhiteListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "addNodeIdentityToWhiteListResponse")
    public JAXBElement<AddNodeIdentityToWhiteListResponse> createAddNodeIdentityToWhiteListResponse(AddNodeIdentityToWhiteListResponse value) {
        return new JAXBElement<AddNodeIdentityToWhiteListResponse>(_AddNodeIdentityToWhiteListResponse_QNAME, AddNodeIdentityToWhiteListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNodeIdentityToBlackListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "addNodeIdentityToBlackListResponse")
    public JAXBElement<AddNodeIdentityToBlackListResponse> createAddNodeIdentityToBlackListResponse(AddNodeIdentityToBlackListResponse value) {
        return new JAXBElement<AddNodeIdentityToBlackListResponse>(_AddNodeIdentityToBlackListResponse_QNAME, AddNodeIdentityToBlackListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteNodeToServerDataRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "executeNodeToServerDataRate")
    public JAXBElement<ExecuteNodeToServerDataRate> createExecuteNodeToServerDataRate(ExecuteNodeToServerDataRate value) {
        return new JAXBElement<ExecuteNodeToServerDataRate>(_ExecuteNodeToServerDataRate_QNAME, ExecuteNodeToServerDataRate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetNodeAccessConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "setNodeAccessConfiguration")
    public JAXBElement<SetNodeAccessConfiguration> createSetNodeAccessConfiguration(SetNodeAccessConfiguration value) {
        return new JAXBElement<SetNodeAccessConfiguration>(_SetNodeAccessConfiguration_QNAME, SetNodeAccessConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNodeEventsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getNodeEventsResponse")
    public JAXBElement<GetNodeEventsResponse> createGetNodeEventsResponse(GetNodeEventsResponse value) {
        return new JAXBElement<GetNodeEventsResponse>(_GetNodeEventsResponse_QNAME, GetNodeEventsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsNodeIdentityBlackListedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "isNodeIdentityBlackListedResponse")
    public JAXBElement<IsNodeIdentityBlackListedResponse> createIsNodeIdentityBlackListedResponse(IsNodeIdentityBlackListedResponse value) {
        return new JAXBElement<IsNodeIdentityBlackListedResponse>(_IsNodeIdentityBlackListedResponse_QNAME, IsNodeIdentityBlackListedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUplinkIpTunnelInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.terminal.service.connode.com/", name = "getUplinkIpTunnelInfo")
    public JAXBElement<GetUplinkIpTunnelInfo> createGetUplinkIpTunnelInfo(GetUplinkIpTunnelInfo value) {
        return new JAXBElement<GetUplinkIpTunnelInfo>(_GetUplinkIpTunnelInfo_QNAME, GetUplinkIpTunnelInfo.class, null, value);
    }

}
