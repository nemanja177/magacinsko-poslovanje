import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PoslovanGodinaComponent } from './list/poslovan-godina.component';
import { PoslovanGodinaDetailComponent } from './detail/poslovan-godina-detail.component';
import { PoslovanGodinaUpdateComponent } from './update/poslovan-godina-update.component';
import { PoslovanGodinaDeleteDialogComponent } from './delete/poslovan-godina-delete-dialog.component';
import { PoslovanGodinaRoutingModule } from './route/poslovan-godina-routing.module';

@NgModule({
  imports: [SharedModule, PoslovanGodinaRoutingModule],
  declarations: [
    PoslovanGodinaComponent,
    PoslovanGodinaDetailComponent,
    PoslovanGodinaUpdateComponent,
    PoslovanGodinaDeleteDialogComponent,
  ],
})
export class PoslovanGodinaModule {}
