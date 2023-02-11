import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJedinicaMere } from '../jedinica-mere.model';
import { JedinicaMereService } from '../service/jedinica-mere.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './jedinica-mere-delete-dialog.component.html',
})
export class JedinicaMereDeleteDialogComponent {
  jedinicaMere?: IJedinicaMere;

  constructor(protected jedinicaMereService: JedinicaMereService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jedinicaMereService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
