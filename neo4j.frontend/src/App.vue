<template>
  <section>>
    <h3 id="title">INENP</h3>
    <div id="btn">
      <button type="button" class="btn btn-primary" v-on:click="this.visualizeAll">Match All</button>
      <button type="button" class="btn btn-warning" v-on:click="this.visualizeErrors">Select Errors</button>
      <button type="button" class="btn btn-warning" v-on:click="this.visualizeStocks">Select critical Stock</button>
    </div>
    <div id="viz"></div>
  </section>
</template>

<script>

import NeoVis from "neovis.js";

export default {
  name: "App",
  components: {},
  data() {
    return {
      matchAll: {
        container_id: "viz",
        server_url: "bolt://13.95.xxx.xxx:7687",
        server_user: "neo4j",
        server_password: "toJADpyjMZC5",
        labels: {
          "Factory": {
            "caption": "location"
          },
          "Endproduct": {
            "caption": "name"
          },
          "Machine": {
            "caption": "name"
          },
          "Material": {
            "caption": "name"
          },
          "Organization": {
            "caption": "name"
          },
          "Product": {
            "caption": "name"
          }
        },
        relationships: {
          "owns": {
            "thickness": "weight",
            "caption": true
          },
          "uses": {
            "thickness": "weight",
            "caption": true
          },
          "needs": {
            "thickness": "weight",
            "caption": true
          },
          "consumes": {
            "thickness": "weight",
            "caption": true
          },
          "produces": {
            "thickness": "weight",
            "caption": true
          }
        },
        initial_cypher: "MATCH p=()-->()\n" +
            "MATCH (n)\n" +
            "RETURN n, p"
      },
        machineError: {
        container_id: "viz",
        server_url: "bolt://13.95.xxx.xxx:7687",
        server_user: "neo4j",
        server_password: "toJADpyjMZC5",
          labels: {
            "Machine": {
              "caption": "name"
            },
            "Product": {
              "caption": "name"
            }
          },
          relationships: {
            "produces": {
              "thickness": "weight",
              "caption": true
            }
          },
          initial_cypher: "MATCH (Machine {error: true})-[r:produces]->(Product)\n" +
              "RETURN Machine, r, Product"
      },
      criticalStock: {
        container_id: "viz",
        server_url: "bolt://13.95.xxx.xxx:7687",
        server_user: "neo4j",
        server_password: "toJADpyjMZC5",
        labels: {
          "Machine": {
            "caption": "name"
          },
          "Material": {
            "caption": "name"
          }
        },
        relationships: {
          "needs": {
            "thickness": "weight",
            "caption": true
          }
        },
        initial_cypher: "MATCH (n:Material)<-[r:needs]-(Machine) WHERE n.stock < 60 RETURN n, r, Machine"
      },
    };
  },
  // operations in mounted get executed before the page is loaded
  mounted() {
    this.visualizeAll()
    },
  methods:{
    visualizeAll:function(){
      var viz;

      viz = new NeoVis(this.matchAll);
      viz.render();
    },
    visualizeErrors:function(){
      var viz;

      viz = new NeoVis(this.machineError);
      viz.render();
    },
    visualizeStocks:function(){
      var viz;

      viz = new NeoVis(this.criticalStock);
      viz.render();
    }
  },
};
</script>

<style>
#title{
  margin-top: 2pc;
  margin-bottom: 2pc;
  text-align: center;
}
#viz {
  width: 100%;
  height: 700px;
  border: 1px solid lightgray;
  font: 10pt arial;

}
#btn {
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 2pc;
}
</style>
