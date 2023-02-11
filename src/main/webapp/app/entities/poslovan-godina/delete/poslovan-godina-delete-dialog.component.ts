import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPoslovanGodina } from '../poslovan-godina.model';
import { PoslovanGodinaService } from '../service/poslovan-godina.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './poslovan-godina-delete-dialog.component.html',
})
export class PoslovanGodinaDeleteDialogComponent {
  poslovanGodina?: IPoslovanGodina;

  constructor(protected poslovanGodinaService: PoslovanGodinaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.poslovanGodinaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
