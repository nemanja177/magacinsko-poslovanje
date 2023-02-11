import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MagacinComponent } from './list/magacin.component';
import { MagacinDetailComponent } from './detail/magacin-detail.component';
import { MagacinUpdateComponent } from './update/magacin-update.component';
import { MagacinDeleteDialogComponent } from './delete/magacin-delete-dialog.component';
import { MagacinRoutingModule } from './route/magacin-routing.module';

@NgModule({
  imports: [SharedModule, MagacinRoutingModule],
  declarations: [MagacinComponent, MagacinDetailComponent, MagacinUpdateComponent, MagacinDeleteDialogComponent],
})
export class MagacinModule {}
