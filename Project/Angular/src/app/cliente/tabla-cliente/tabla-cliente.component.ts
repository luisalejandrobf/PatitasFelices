import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ClienteService} from "../../service/cliente/cliente.service";
import {PacienteService} from "../../service/paciente/paciente.service";
import {Paciente} from "../../paciente/paciente";
import {Cliente} from "../cliente";

@Component({
  selector: 'app-tabla-cliente',
  templateUrl: './tabla-cliente.component.html',
  styleUrls: ['./tabla-cliente.component.css']
})
export class TablaClienteComponent {

  constructor(private clienteService: ClienteService, private pacienteService: PacienteService) {}

  @Input() clienteLista: Cliente[] = [];
  @Output() verInformacionCliente = new EventEmitter<Cliente>();
  @Output() modificarCliente = new EventEmitter<Cliente>();


  informacionClientes(cliente: Cliente) {
    this.verInformacionCliente.emit(cliente);
  }

  modificarClientes(cliente: Cliente) {
    this.modificarCliente.emit(cliente);
  }

  eliminarClientes(cliente: Cliente) {
    const clienteIdEliminar = this.clienteLista.indexOf(cliente);
    console.log(clienteIdEliminar);

    if (clienteIdEliminar > -1) {
      this.clienteLista.splice(clienteIdEliminar, 1);
      this.clienteService.eliminarCliente(clienteIdEliminar).subscribe(response => {
        console.log('Respuesta al eliminar cliente con ID', clienteIdEliminar, ':', response);
      });
    }
  }


}