/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bcits.utility.amr;

/**
 *
 * @author userravi
 */
public class EventManager {

    public static String getEventDescription(String eventCode) {
        String eventDescription = null;

        switch (Integer.parseInt(eventCode.trim())) {
            // Table 32 Indian Event Reference Table – Voltage Related 
            case 1:
                eventDescription = "R-Phase – PT link Missing (Missing Potential) – Occurrence";
                break;
            case 2:
                eventDescription = "R-Phase – PT link Missing (Missing Potential) – Restoration";
                break;
            case 3:
                eventDescription = "Y-Phase – PT link Missing (Missing Potential) – Occurrence";
                break;
            case 4:
                eventDescription = "Y-Phase – PT link Missing (Missing Potential) – Restoration";
                break;
            case 5:
                eventDescription = "Y-Phase – PT link Missing (Missing Potential) – Restoration";
                break;
            case 6:
                eventDescription = "B-Phase – PT link Missing (Missing Potential) – Occurrence";
                break;
            case 7:
                eventDescription = "Over Voltage in any Phase  - Occurrence";
                break;
            case 8:
                eventDescription = "Over Voltage in any Phase  - Restoration";
                break;
            case 9:
                eventDescription = "Low Voltage in any Phase  - Occurrence";
                break;
            case 10:
                eventDescription = "Low Voltage in any Phase  - Restoration";
                break;
            case 11:
                eventDescription = "Voltage Unbalance - Occurrence";
                break;
            case 12:
                eventDescription = "Voltage Unbalance  - Restoration";
                break;
            //Table 33 Indian Event Reference Table – Current Related 
            case 51:
                eventDescription = "Phase – R CT reverse – Occurrence";
                break;
            case 52:
                eventDescription = "Phase – R CT reverse – Restoration";
                break;
            case 53:
                eventDescription = "Phase – Y CT reverse – Occurrence";
                break;
            case 54:
                eventDescription = "Phase – Y CT reverse – Restoration";
                break;
            case 55:
                eventDescription = "Phase – B CT reverse – Occurrence";
                break;
            case 56:
                eventDescription = "Phase – B CT reverse – Restoration";
                break;
            case 57:
                eventDescription = "Phase – R CT Open  - Occurrence";
                break;
            case 58:
                eventDescription = "Phase – R CT Open  - Restoration";
                break;
            case 59:
                eventDescription = "Phase – Y CT Open  - Occurrence";
                break;
            case 60:
                eventDescription = "Phase – Y CT Open  - Restoration";
                break;
            case 61:
                eventDescription = "Phase – B CT Open  - Occurrence";
                break;
            case 62:
                eventDescription = "Phase – B CT Open  - Restoration";
                break;
            case 63:
                eventDescription = "Current Unbalance  - Occurrence";
                break;
            case 64:
                eventDescription = "Current Unbalance  - Restoration";
                break;
            case 65:
                eventDescription = "CT Bypass – Occurrence";
                break;
            case 66:
                eventDescription = "CT Bypass – Restoration";
                break;
            case 67:
                eventDescription = "Over Current in any Phase – Occurrence";
                break;
            case 68:
                eventDescription = "Over Current in any Phase – Restoration ";
                break;
            //Table 34 Indian Event Reference Table – Power Related 
            case 101:
                eventDescription = "Power failure (3 phase) – Occurrence ";
                break;
            case 102:
                eventDescription = "Power failure (3 phase) – Restoration";
                break;
            //Table 35 Indian Event Reference Table – Transaction Related 
            case 151:
                eventDescription = "Real Time Clock – Date and Time";
                break;
            case 152:
                eventDescription = "Demand Integration Period";
                break;
            case 153:
                eventDescription = "Profile Capture Period";
                break;
            case 154:
                eventDescription = "Single-action Schedule for Billing Dates";
                break;
            case 155:
                eventDescription = "Activity Calendar for Time Zones etc.";
                break;
            // Table 36 Indian Event Reference Table – Others
            case 201:
                eventDescription = "Influence of Permanent Magnet or AC/ DC Electromagnet - Occurrence ";
                break;
            case 202:
                eventDescription = "Influence of Permanent Magnet or AC/ DC Electromagnet  - Restoration ";
                break;
            case 203:
                eventDescription = "Neutral Disturbance  - HF & DC  - Occurrence ";
                break;
            case 204:
                eventDescription = "Neutral Disturbance  - HF & DC  - Restoration";
                break;
            case 205:
                eventDescription = "Very Low PF  - Occurrence";
                break;
            case 206:
                eventDescription = "Very Low PF  - Restoration";
                break;
            //Table 37 Indian Event Reference Table – Non Roll Over Events 
            case 251:
                eventDescription = "Meter Cover Opening – Occurrence";
                break;
            // Table 38 Indian Event Reference Table – Control Events 
            case 301:
                eventDescription = "Meter disconnected";
                break;
            case 302:
                eventDescription = "Meter connected";
                break;

            default:
                eventDescription = "Invalid Event!!!";
                break;
        }
      //  System.out.println(eventDescription);

        return eventDescription.trim();
    }


}
