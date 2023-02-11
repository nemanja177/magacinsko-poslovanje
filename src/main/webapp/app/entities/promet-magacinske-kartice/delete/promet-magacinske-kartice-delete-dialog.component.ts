import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';
import { PrometMagacinskeKarticeService } from '../service/promet-magacinske-kartice.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './promet-magacinske-kartice-delete-dialog.component.html',
})
export class PrometMagacinskeKarticeDeleteDialogComponent {
  prometMagacinskeKartice?: IPrometMagacinskeKartice;

  constructor(protected prometMagacinskeKarticeService: PrometMagacinskeKarticeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prometMagacinskeKarticeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
