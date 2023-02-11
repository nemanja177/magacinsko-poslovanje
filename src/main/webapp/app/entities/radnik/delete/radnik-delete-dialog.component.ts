import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRadnik } from '../radnik.model';
import { RadnikService } from '../service/radnik.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './radnik-delete-dialog.component.html',
})
export class RadnikDeleteDialogComponent {
  radnik?: IRadnik;

  constructor(protected radnikService: RadnikService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.radnikService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
