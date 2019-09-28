<template>
  <div class="content" id="js-drop-zone">

  <div class="message error">
        <div class="note">
          <p>Ooops, we could not display the BPMN 2.0 diagram.</p>

          <div class="details">
            <span>cause of the problem</span>
            <pre></pre>
          </div>
        </div>
      </div>
     <div class="canvas" id="js-canvas"></div>
      </div>
</template>

<script>

import $ from 'jquery';

import BpmnModeler from 'bpmn-js/lib/Modeler';

export default {
    methods: {

    createNewDiagram () {
      this.openDiagram(this.diagramXML)
    },
    openDiagram(xml){
        let self = this;
        this.modeler.importXML(xml, function(err) {

            if (err) {
              self.container
                .removeClass('with-diagram')
                .addClass('with-error');

              self.container.find('.error pre').text(err.message);

              console.error(err);
            } else {
              self.container
                .removeClass('with-error')
                .addClass('with-diagram');
            }
      });
    },

    },

    data() {
        return {
            container: null,
            modeler: null,
            diagramXML: '<?xml version="1.0" encoding="UTF-8"?>' +
                         '<bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd" id="sample-diagram" targetNamespace="http://bpmn.io/schema/bpmn">' +
                           '<bpmn2:process id="Process_1" isExecutable="false">' +
                             '<bpmn2:startEvent id="StartEvent_1"/>' +
                           '</bpmn2:process>' +
                           '<bpmndi:BPMNDiagram id="BPMNDiagram_1">' +
                             '<bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">' +
                               '<bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">' +
                                 '<dc:Bounds height="36.0" width="36.0" x="412.0" y="240.0"/>' +
                               '</bpmndi:BPMNShape>' +
                            '</bpmndi:BPMNPlane>' +
                           '</bpmndi:BPMNDiagram>' +
                         '</bpmn2:definitions>'
        }
    },


    mounted() {

    this.container = $('#js-drop-zone');

    this.modeler = new BpmnModeler({
      container: '#js-canvas'
    });
   this.createNewDiagram();

    }
}
</script>
